package br.com.probelle.sistemas.probelle_tms.v1.dto.manifest;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ManifestResponseDTO(
    UUID uuid,
    String manifestNo,
    UUID customerUuid,
    UUID carrierUuid,
    UUID originRegionUuid,
    UUID destRegionUuid,
    ManifestStatus status,
    LocalDateTime lockedAt,
    BigDecimal totalWeightKg,
    BigDecimal totalValue) {}
