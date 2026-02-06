package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleResponseDTO;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreightRuleService {
  FreightRuleResponseDTO create(FreightRuleRequestDTO request);

  FreightRuleResponseDTO update(UUID uuid, FreightRuleRequestDTO request);

  FreightRuleResponseDTO getByUuid(UUID uuid);

  FreightRuleResponseDTO findRuleForWeight(
      UUID freightTableUuid, UUID originRegionUuid, UUID destRegionUuid, BigDecimal weight);

  Page<FreightRuleResponseDTO> list(Pageable pageable);

  void delete(UUID uuid);
}
