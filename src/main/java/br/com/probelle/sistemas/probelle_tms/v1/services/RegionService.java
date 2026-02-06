package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionResponseDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegionService {
  RegionResponseDTO create(RegionRequestDTO request);

  RegionResponseDTO update(UUID uuid, RegionRequestDTO request);

  RegionResponseDTO getByUuid(UUID uuid);

  RegionResponseDTO getByCode(String code);

  Page<RegionResponseDTO> list(Pageable pageable);

  void delete(UUID uuid);
}
