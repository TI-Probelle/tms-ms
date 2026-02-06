package br.com.probelle.sistemas.probelle_tms.v1.dto.manifestinvoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ManifestInvoiceResponseDTO(
    UUID uuid,
    UUID manifestUuid,
    UUID invoiceUuid,
    Integer seq,
    BigDecimal weightKg,
    BigDecimal value,
    LocalDateTime linkedAt) {}
