package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierResponseDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarrierService {
  CarrierResponseDTO create(CarrierRequestDTO request);

  CarrierResponseDTO update(UUID uuid, CarrierRequestDTO request);

  CarrierResponseDTO getByUuid(UUID uuid);

  CarrierResponseDTO getByCode(String code);

  Page<CarrierResponseDTO> list(Pageable pageable);

  void delete(UUID uuid);
}
