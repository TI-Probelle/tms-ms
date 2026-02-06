package br.com.probelle.sistemas.probelle_tms.v1.dto.carrier;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.CarrierType;
import java.util.UUID;

public record CarrierResponseDTO(
    UUID uuid,
    String code,
    String name,
    CarrierType carrierType,
    String docNumber,
    Boolean active) {}
