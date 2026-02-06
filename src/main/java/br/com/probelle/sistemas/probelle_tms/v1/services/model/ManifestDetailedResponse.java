package br.com.probelle.sistemas.probelle_tms.v1.services.model;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestinvoice.ManifestInvoiceResponseDTO;
import java.util.List;

public record ManifestDetailedResponse(
    ManifestResponseDTO manifest,
    List<ManifestInvoiceResponseDTO> invoices,
    ManifestFreightResponseDTO freight) {}
