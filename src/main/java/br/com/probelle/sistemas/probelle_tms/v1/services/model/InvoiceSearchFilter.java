package br.com.probelle.sistemas.probelle_tms.v1.services.model;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceSearchFilter(
    UUID customerUuid,
    UUID originRegionUuid,
    UUID destRegionUuid,
    InvoiceStatus status,
    LocalDate issueDateFrom,
    LocalDate issueDateTo) {}
