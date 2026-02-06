package br.com.probelle.sistemas.probelle_tms.v1.dto.invoice;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceRequestDTO(
    String nfKey,
    String nfNumber,
    String series,
    String model,
    LocalDate issueDate,
    UUID customerUuid,
    UUID originRegionUuid,
    UUID destRegionUuid,
    BigDecimal grossValue,
    BigDecimal netValue,
    BigDecimal weightKg,
    Integer volumeQty,
    InvoiceStatus status) {}
