package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.InvoiceSearchFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
  InvoiceResponseDTO create(InvoiceRequestDTO request);

  Page<InvoiceResponseDTO> search(InvoiceSearchFilter filter, Pageable pageable);

  InvoiceResponseDTO getByUuid(UUID uuid);
}
