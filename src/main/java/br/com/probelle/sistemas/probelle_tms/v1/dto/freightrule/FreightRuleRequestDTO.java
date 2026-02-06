package br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule;

import java.math.BigDecimal;
import java.util.UUID;

public record FreightRuleRequestDTO(
    UUID freightTableUuid,
    UUID originRegionUuid,
    UUID destRegionUuid,
    BigDecimal weightBreakFrom,
    BigDecimal weightBreakTo,
    BigDecimal ratePerKg,
    BigDecimal minimumFreight,
    BigDecimal adValoremPct,
    BigDecimal grisPct,
    BigDecimal tollFixed,
    BigDecimal dispatchFixed) {}
