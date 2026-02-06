package br.com.probelle.sistemas.probelle_tms.v1.dto.region;

import java.util.UUID;

public record RegionResponseDTO(
    UUID uuid,
    String code,
    String name,
    String country,
    String state,
    String city) {}
