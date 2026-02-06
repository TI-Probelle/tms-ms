package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Carrier;
import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightTable;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.FreightTableMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.FreightTableRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.FreightTableService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FreightTableServiceImpl extends ServiceBase implements FreightTableService {

  @Autowired private FreightTableRepository freightTableRepository;

  @Autowired private CarrierRepository carrierRepository;

  @Autowired private FreightTableMapper freightTableMapper;

  @Override
  @Transactional
  public FreightTableResponseDTO create(FreightTableRequestDTO request) {
    FreightTable entity = freightTableMapper.toEntity(request);
    entity.setCarrier(resolveCarrier(request.carrierUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    validateDates(entity.getValidFrom(), entity.getValidTo());
    FreightTable saved = freightTableRepository.save(entity);
    return freightTableMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public FreightTableResponseDTO update(UUID uuid, FreightTableRequestDTO request) {
    FreightTable entity = requireByUuid(freightTableRepository, uuid, "FreightTable");
    freightTableMapper.updateEntity(request, entity);
    entity.setCarrier(resolveCarrier(request.carrierUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    validateDates(entity.getValidFrom(), entity.getValidTo());
    FreightTable saved = freightTableRepository.save(entity);
    return freightTableMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public FreightTableResponseDTO getByUuid(UUID uuid) {
    FreightTable entity = requireByUuid(freightTableRepository, uuid, "FreightTable");
    return freightTableMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<FreightTableResponseDTO> list(Pageable pageable) {
    return freightTableRepository.findAll(pageable).map(freightTableMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FreightTableResponseDTO> listActiveByCarrier(UUID carrierUuid) {
    Carrier carrier = requireByUuid(carrierRepository, carrierUuid, "Carrier");
    return freightTableRepository.findByCarrierIdAndActiveTrueOrderByValidFromDesc(carrier.getId())
        .stream()
        .map(freightTableMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public FreightTableResponseDTO findActiveForCarrierOnDate(UUID carrierUuid, LocalDate refDate) {
    Carrier carrier = requireByUuid(carrierRepository, carrierUuid, "Carrier");
    FreightTable entity =
        freightTableRepository
            .findActiveForCarrierOnDate(carrier.getId(), refDate)
            .orElseThrow(() -> new ValidationException("No active freight table"));
    return freightTableMapper.toResponse(entity);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    FreightTable entity = requireByUuid(freightTableRepository, uuid, "FreightTable");
    freightTableRepository.delete(entity);
  }

  private Carrier resolveCarrier(UUID uuid) {
    return requireByUuid(carrierRepository, uuid, "Carrier");
  }

  private void validateDates(LocalDate from, LocalDate to) {
    if (from != null && to != null && from.isAfter(to)) {
      throw new ValidationException("validFrom must be before validTo");
    }
  }
}
