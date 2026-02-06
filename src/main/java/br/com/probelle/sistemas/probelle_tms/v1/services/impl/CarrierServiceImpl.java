package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Carrier;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.CarrierMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.CarrierService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarrierServiceImpl extends ServiceBase implements CarrierService {

  @Autowired private CarrierRepository carrierRepository;

  @Autowired private CarrierMapper carrierMapper;

  @Override
  @Transactional
  public CarrierResponseDTO create(CarrierRequestDTO request) {
    if (carrierRepository.existsByCode(request.code())) {
      throw new ConflictException("Carrier code already exists");
    }
    Carrier entity = carrierMapper.toEntity(request);
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    Carrier saved = carrierRepository.save(entity);
    return carrierMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public CarrierResponseDTO update(UUID uuid, CarrierRequestDTO request) {
    Carrier entity = requireByUuid(carrierRepository, uuid, "Carrier");
    if (request.code() != null && !request.code().equals(entity.getCode())) {
      if (carrierRepository.existsByCode(request.code())) {
        throw new ConflictException("Carrier code already exists");
      }
    }
    carrierMapper.updateEntity(request, entity);
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    Carrier saved = carrierRepository.save(entity);
    return carrierMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public CarrierResponseDTO getByUuid(UUID uuid) {
    Carrier entity = requireByUuid(carrierRepository, uuid, "Carrier");
    return carrierMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public CarrierResponseDTO getByCode(String code) {
    Carrier entity =
        carrierRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException("Carrier not found"));
    return carrierMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CarrierResponseDTO> list(Pageable pageable) {
    return carrierRepository.findAll(pageable).map(carrierMapper::toResponse);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    Carrier entity = requireByUuid(carrierRepository, uuid, "Carrier");
    carrierRepository.delete(entity);
  }
}
