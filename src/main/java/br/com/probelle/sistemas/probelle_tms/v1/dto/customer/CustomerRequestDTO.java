package br.com.probelle.sistemas.probelle_tms.v1.dto.customer;

import java.util.UUID;

public record CustomerRequestDTO(
    String code,
    String name,
    UUID defaultRegionUuid,
    Boolean active) {}
