package br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiDirection;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiType;
import java.time.LocalDateTime;
import java.util.UUID;

public record EdiDocumentResponseDTO(
    UUID uuid,
    UUID carrierUuid,
    EdiType ediType,
    String version,
    EdiDirection direction,
    String fileName,
    LocalDateTime receivedAt,
    LocalDateTime generatedAt,
    EdiStatus status,
    String rawContent) {}
