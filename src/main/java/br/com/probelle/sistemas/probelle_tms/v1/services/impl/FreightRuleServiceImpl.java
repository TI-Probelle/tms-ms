package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightRule;
import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightTable;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Region;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.FreightRuleMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.FreightRuleRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.FreightTableRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.RegionRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.FreightRuleService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FreightRuleServiceImpl extends ServiceBase implements FreightRuleService {

  @Autowired private FreightRuleRepository freightRuleRepository;

  @Autowired private FreightTableRepository freightTableRepository;

  @Autowired private RegionRepository regionRepository;

  @Autowired private FreightRuleMapper freightRuleMapper;

  @Override
  @Transactional
  public FreightRuleResponseDTO create(FreightRuleRequestDTO request) {
    FreightRule entity = freightRuleMapper.toEntity(request);
    entity.setFreightTable(resolveFreightTable(request.freightTableUuid()));
    entity.setOriginRegion(resolveRegion(request.originRegionUuid()));
    entity.setDestRegion(resolveRegion(request.destRegionUuid()));
    validateBreaks(entity.getWeightBreakFrom(), entity.getWeightBreakTo());
    FreightRule saved = freightRuleRepository.save(entity);
    return freightRuleMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public FreightRuleResponseDTO update(UUID uuid, FreightRuleRequestDTO request) {
    FreightRule entity = requireByUuid(freightRuleRepository, uuid, "FreightRule");
    freightRuleMapper.updateEntity(request, entity);
    entity.setFreightTable(resolveFreightTable(request.freightTableUuid()));
    entity.setOriginRegion(resolveRegion(request.originRegionUuid()));
    entity.setDestRegion(resolveRegion(request.destRegionUuid()));
    validateBreaks(entity.getWeightBreakFrom(), entity.getWeightBreakTo());
    FreightRule saved = freightRuleRepository.save(entity);
    return freightRuleMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public FreightRuleResponseDTO getByUuid(UUID uuid) {
    FreightRule entity = requireByUuid(freightRuleRepository, uuid, "FreightRule");
    return freightRuleMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public FreightRuleResponseDTO findRuleForWeight(
      UUID freightTableUuid, UUID originRegionUuid, UUID destRegionUuid, BigDecimal weight) {
    Long tableId = requireIdByUuid(freightTableRepository, freightTableUuid, "FreightTable");
    Long originId = requireIdByUuid(regionRepository, originRegionUuid, "Region");
    Long destId = requireIdByUuid(regionRepository, destRegionUuid, "Region");
    FreightRule rule =
        freightRuleRepository
            .findRuleForWeight(tableId, originId, destId, weight)
            .orElseThrow(() -> new ValidationException("Freight rule not found"));
    return freightRuleMapper.toResponse(rule);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<FreightRuleResponseDTO> list(Pageable pageable) {
    return freightRuleRepository.findAll(pageable).map(freightRuleMapper::toResponse);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    FreightRule entity = requireByUuid(freightRuleRepository, uuid, "FreightRule");
    freightRuleRepository.delete(entity);
  }

  private FreightTable resolveFreightTable(UUID uuid) {
    return requireByUuid(freightTableRepository, uuid, "FreightTable");
  }

  private Region resolveRegion(UUID uuid) {
    return requireByUuid(regionRepository, uuid, "Region");
  }

  private void validateBreaks(BigDecimal from, BigDecimal to) {
    if (from != null && to != null && from.compareTo(to) > 0) {
      throw new ValidationException("weightBreakFrom must be <= weightBreakTo");
    }
  }
}
