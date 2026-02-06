package br.com.probelle.sistemas.probelle_tms.v1.services.model;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingMode;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record ManifestFreightPriceRequest(
    ManifestFreightPricingSource pricingSource,
    ManifestFreightPricingMode pricingMode,
    UUID freightTableUuid,
    BigDecimal freightTotal,
    Map<String, Object> breakdown,
    Long negotiatedByUserId,
    LocalDateTime negotiatedAt,
    String notes) {}
