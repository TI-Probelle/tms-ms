package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Carrier;
import br.com.probelle.sistemas.probelle_tms.v1.entity.CarrierDriver;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.CarrierDriverMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierDriverRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.CarrierDriverService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarrierDriverServiceImpl extends ServiceBase implements CarrierDriverService {

  @Autowired private CarrierDriverRepository carrierDriverRepository;

  @Autowired private CarrierRepository carrierRepository;

  @Autowired private CarrierDriverMapper carrierDriverMapper;

  @Override
  @Transactional
  public CarrierDriverResponseDTO create(CarrierDriverRequestDTO request) {
    CarrierDriver entity = carrierDriverMapper.toEntity(request);
    entity.setCarrier(resolveCarrier(request.carrierUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    CarrierDriver saved = carrierDriverRepository.save(entity);
    return carrierDriverMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public CarrierDriverResponseDTO update(UUID uuid, CarrierDriverRequestDTO request) {
    CarrierDriver entity = requireByUuid(carrierDriverRepository, uuid, "CarrierDriver");
    carrierDriverMapper.updateEntity(request, entity);
    entity.setCarrier(resolveCarrier(request.carrierUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    CarrierDriver saved = carrierDriverRepository.save(entity);
    return carrierDriverMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public CarrierDriverResponseDTO getByUuid(UUID uuid) {
    CarrierDriver entity = requireByUuid(carrierDriverRepository, uuid, "CarrierDriver");
    return carrierDriverMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CarrierDriverResponseDTO> listActiveByCarrier(UUID carrierUuid) {
    Carrier carrier = requireByUuid(carrierRepository, carrierUuid, "Carrier");
    return carrierDriverRepository
        .findByCarrierIdAndActiveTrueOrderByNameAsc(carrier.getId())
        .stream()
        .map(carrierDriverMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    CarrierDriver entity = requireByUuid(carrierDriverRepository, uuid, "CarrierDriver");
    carrierDriverRepository.delete(entity);
  }

  private Carrier resolveCarrier(UUID uuid) {
    return requireByUuid(carrierRepository, uuid, "Carrier");
  }
}
