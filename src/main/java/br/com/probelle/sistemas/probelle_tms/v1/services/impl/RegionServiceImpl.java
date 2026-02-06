package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Region;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.RegionMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.RegionRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.RegionService;
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
public class RegionServiceImpl extends ServiceBase implements RegionService {

  @Autowired private RegionRepository regionRepository;

  @Autowired private RegionMapper regionMapper;

  @Override
  @Transactional
  public RegionResponseDTO create(RegionRequestDTO request) {
    if (regionRepository.existsByCode(request.code())) {
      throw new ConflictException("Region code already exists");
    }
    Region entity = regionMapper.toEntity(request);
    Region saved = regionRepository.save(entity);
    return regionMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public RegionResponseDTO update(UUID uuid, RegionRequestDTO request) {
    Region entity = requireByUuid(regionRepository, uuid, "Region");
    if (request.code() != null && !request.code().equals(entity.getCode())) {
      if (regionRepository.existsByCode(request.code())) {
        throw new ConflictException("Region code already exists");
      }
    }
    regionMapper.updateEntity(request, entity);
    Region saved = regionRepository.save(entity);
    return regionMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public RegionResponseDTO getByUuid(UUID uuid) {
    Region entity = requireByUuid(regionRepository, uuid, "Region");
    return regionMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public RegionResponseDTO getByCode(String code) {
    Region entity =
        regionRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException("Region not found"));
    return regionMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RegionResponseDTO> list(Pageable pageable) {
    return regionRepository.findAll(pageable).map(regionMapper::toResponse);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    Region entity = requireByUuid(regionRepository, uuid, "Region");
    regionRepository.delete(entity);
  }
}
