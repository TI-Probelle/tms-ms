package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestFreight;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.CarrierType;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.FreightTableMode;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingMode;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingSource;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.ManifestFreightMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.FreightRuleRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.FreightTableRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestFreightRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.PricingService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.BusinessRuleException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import br.com.probelle.sistemas.probelle_tms.v1.services.guards.ManifestGuards;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestFreightPriceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PricingServiceImpl extends ServiceBase implements PricingService {

  @Autowired
  private ManifestRepository manifestRepository;

  @Autowired
  private ManifestFreightRepository manifestFreightRepository;

  @Autowired
  private FreightTableRepository freightTableRepository;

  @Autowired
  private FreightRuleRepository freightRuleRepository;

  @Autowired
  private ManifestFreightMapper manifestFreightMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  @Transactional
  public ManifestFreightResponseDTO price(UUID manifestUuid, ManifestFreightPriceRequest request) {
    if (request == null || request.pricingSource() == null) {
      throw new ValidationException("Pricing source is required");
    }
    Manifest manifest = requireByUuid(manifestRepository, manifestUuid, "Manifest");
    ManifestGuards.assertNotLocked(manifest);

    ManifestFreight freight =
        manifestFreightRepository
            .findByManifestId(manifest.getId())
            .orElseGet(ManifestFreight::new);
    freight.setManifest(manifest);

    if (request.pricingSource() == ManifestFreightPricingSource.TABLE) {
      if (manifest.getCarrier().getCarrierType() == CarrierType.AGREGADO) {
        throw new ConflictException("Aggregated carrier cannot use TABLE pricing");
      }
      applyTablePricing(manifest, freight, request);
    } else {
      applyNegotiatedPricing(manifest, freight, request);
    }

    ManifestFreight saved = manifestFreightRepository.save(freight);
    ManifestFreight persisted = manifestFreightRepository.findById(saved.getId()).orElse(saved);
    return manifestFreightMapper.toResponse(persisted);
  }

  private void applyTablePricing(
      Manifest manifest, ManifestFreight freight, ManifestFreightPriceRequest request) {
    LocalDate refDate =
        manifest.getCreatedAt() != null ? manifest.getCreatedAt().toLocalDate() : LocalDate.now();
    var table =
        freightTableRepository
            .findActiveForCarrierOnDate(manifest.getCarrier().getId(), refDate)
            .orElseThrow(() -> new BusinessRuleException("No active freight table for carrier"));

    var rule =
        freightRuleRepository
            .findRuleForWeight(
                table.getId(),
                manifest.getOriginRegion().getId(),
                manifest.getDestRegion().getId(),
                manifest.getTotalWeightKg())
            .orElseThrow(() -> new BusinessRuleException("No freight rule for weight"));

    BigDecimal weight =
        manifest.getTotalWeightKg() != null ? manifest.getTotalWeightKg() : BigDecimal.ZERO;
    BigDecimal baseValue =
        manifest.getTotalValue() != null ? manifest.getTotalValue() : BigDecimal.ZERO;

    BigDecimal perKg = weight.multiply(rule.getRatePerKg());
    BigDecimal baseFreight = perKg.max(rule.getMinimumFreight());
    boolean minimumApplied = perKg.compareTo(rule.getMinimumFreight()) < 0;

    BigDecimal adValorem =
        baseValue
            .multiply(rule.getAdValoremPct())
            .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
    BigDecimal gris =
        baseValue
            .multiply(rule.getGrisPct())
            .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

    BigDecimal total =
        baseFreight.add(adValorem).add(gris).add(rule.getTollFixed()).add(rule.getDispatchFixed());

    freight.setPricingSource(ManifestFreightPricingSource.TABLE);
    freight.setPricingMode(mapPricingMode(table.getMode()));
    freight.setFreightTable(table);
    freight.setBaseWeightKg(weight);
    freight.setBaseValue(baseValue);
    freight.setFreightTotal(total.setScale(2, RoundingMode.HALF_UP));
    freight.setBreakdownJson(
        toJson(
            Map.of(
                "perKg", perKg,
                "baseFreight", baseFreight,
                "minimumApplied", minimumApplied,
                "adValorem", adValorem,
                "gris", gris,
                "tollFixed", rule.getTollFixed(),
                "dispatchFixed", rule.getDispatchFixed())));
    freight.setNegotiatedByUserId(null);
    freight.setNegotiatedAt(null);
    freight.setNotes(null);
  }

  private void applyNegotiatedPricing(
      Manifest manifest, ManifestFreight freight, ManifestFreightPriceRequest request) {
    if (request.pricingSource() == ManifestFreightPricingSource.TABLE) {
      throw new ValidationException("Pricing source required");
    }
    if (request.freightTotal() == null || request.freightTotal().compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidationException("Freight total must be greater than zero");
    }
    if (request.pricingMode() == null) {
      throw new ValidationException("Pricing mode is required");
    }
    freight.setPricingSource(request.pricingSource());
    freight.setPricingMode(request.pricingMode());
    freight.setFreightTable(null);
    freight.setBaseWeightKg(
        manifest.getTotalWeightKg() != null ? manifest.getTotalWeightKg() : BigDecimal.ZERO);
    freight.setBaseValue(
        manifest.getTotalValue() != null ? manifest.getTotalValue() : BigDecimal.ZERO);
    freight.setFreightTotal(request.freightTotal().setScale(2, RoundingMode.HALF_UP));
    freight.setBreakdownJson(toJson(request.breakdown()));
    freight.setNegotiatedByUserId(request.negotiatedByUserId());
    freight.setNegotiatedAt(request.negotiatedAt());
    freight.setNotes(request.notes());
  }

  private ManifestFreightPricingMode mapPricingMode(FreightTableMode mode) {
    if (mode == null) {
      return ManifestFreightPricingMode.MIXED;
    }
    return switch (mode) {
      case KG -> ManifestFreightPricingMode.KG;
      case FIXED -> ManifestFreightPricingMode.FIXED;
      case MIXED -> ManifestFreightPricingMode.MIXED;
    };
  }

  private String toJson(Map<String, Object> data) {
    if (data == null || data.isEmpty()) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException ex) {
      throw new ValidationException("Invalid breakdown JSON");
    }
  }
}
