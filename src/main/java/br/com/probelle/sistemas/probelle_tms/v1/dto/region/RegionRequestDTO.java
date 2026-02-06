package br.com.probelle.sistemas.probelle_tms.v1.dto.region;

public record RegionRequestDTO(
    String code,
    String name,
    String country,
    String state,
    String city) {}
