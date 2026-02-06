package br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingMode;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ManifestFreightRequestDTO(
    UUID manifestUuid,
    ManifestFreightPricingSource pricingSource,
    ManifestFreightPricingMode pricingMode,
    UUID freightTableUuid,
    BigDecimal baseWeightKg,
    BigDecimal baseValue,
    BigDecimal freightTotal,
    String breakdownJson,
    Long negotiatedByUserId,
    LocalDateTime negotiatedAt,
    String notes) {}
