package br.com.probelle.sistemas.probelle_tms.v1.dto.ediref;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiRefType;
import java.util.UUID;

public record EdiRefRequestDTO(
    UUID ediDocumentUuid,
    EdiRefType refType,
    Long refId) {}
