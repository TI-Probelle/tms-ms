package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverResponseDTO;
import java.util.List;
import java.util.UUID;

public interface CarrierDriverService {
  CarrierDriverResponseDTO create(CarrierDriverRequestDTO request);

  CarrierDriverResponseDTO update(UUID uuid, CarrierDriverRequestDTO request);

  CarrierDriverResponseDTO getByUuid(UUID uuid);

  List<CarrierDriverResponseDTO> listActiveByCarrier(UUID carrierUuid);

  void delete(UUID uuid);
}
