package br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver;

import java.util.UUID;

public record CarrierDriverRequestDTO(
    UUID carrierUuid,
    String name,
    String docNumber,
    String plateMain,
    String plateTrailer,
    Boolean active) {}
