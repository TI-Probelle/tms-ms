package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestFreightPriceRequest;
import java.util.UUID;

public interface PricingService {
  ManifestFreightResponseDTO price(UUID manifestUuid, ManifestFreightPriceRequest request);
}
