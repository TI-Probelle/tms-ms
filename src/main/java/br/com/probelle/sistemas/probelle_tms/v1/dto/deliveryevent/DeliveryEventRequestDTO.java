package br.com.probelle.sistemas.probelle_tms.v1.dto.deliveryevent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryEventRequestDTO(
    UUID manifestUuid,
    UUID invoiceUuid,
    UUID ediDocumentUuid,
    String occurrenceCode,
    String occurrenceDesc,
    LocalDateTime occurredAt,
    String note) {}
