package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.GenerateNotfisRequest;

public interface EdiNotfisService {
  EdiDocumentResponseDTO generateNotfis(GenerateNotfisRequest request);
}
