package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.AddInvoicesRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.LockRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestDetailedResponse;
import java.util.UUID;

public interface ManifestService {
  ManifestResponseDTO create(ManifestRequestDTO request);

  ManifestDetailedResponse addInvoices(UUID manifestUuid, AddInvoicesRequest request);

  ManifestDetailedResponse getDetailed(UUID manifestUuid);

  ManifestResponseDTO lock(UUID manifestUuid, LockRequest request);
}
