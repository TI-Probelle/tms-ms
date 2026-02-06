package br.com.probelle.sistemas.probelle_tms.v1.dto.carrier;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.CarrierType;

public record CarrierRequestDTO(
    String code,
    String name,
    CarrierType carrierType,
    String docNumber,
    Boolean active) {}
