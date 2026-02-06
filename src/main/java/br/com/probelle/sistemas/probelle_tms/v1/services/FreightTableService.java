package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableResponseDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreightTableService {
  FreightTableResponseDTO create(FreightTableRequestDTO request);

  FreightTableResponseDTO update(UUID uuid, FreightTableRequestDTO request);

  FreightTableResponseDTO getByUuid(UUID uuid);

  Page<FreightTableResponseDTO> list(Pageable pageable);

  List<FreightTableResponseDTO> listActiveByCarrier(UUID carrierUuid);

  FreightTableResponseDTO findActiveForCarrierOnDate(UUID carrierUuid, LocalDate refDate);

  void delete(UUID uuid);
}
