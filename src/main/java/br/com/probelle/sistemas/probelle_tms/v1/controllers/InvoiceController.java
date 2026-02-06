package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import br.com.probelle.sistemas.probelle_tms.v1.services.InvoiceService;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.InvoiceSearchFilter;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class InvoiceController {

  @Autowired private InvoiceService invoiceService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceResponseDTO> create(@RequestBody InvoiceRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.create(request));
  }

  @GetMapping("/{invoiceUuid}")
  public ResponseEntity<InvoiceResponseDTO> getByUuid(@PathVariable UUID invoiceUuid) {
    return ResponseEntity.ok(invoiceService.getByUuid(invoiceUuid));
  }

  @GetMapping
  public ResponseEntity<Page<InvoiceResponseDTO>> search(
      @RequestParam(required = false) InvoiceStatus status,
      @RequestParam(required = false) UUID customerUuid,
      @RequestParam(required = false) UUID originRegionUuid,
      @RequestParam(required = false) UUID destRegionUuid,
      @RequestParam(required = false) LocalDate issueDateFrom,
      @RequestParam(required = false) LocalDate issueDateTo,
      Pageable pageable) {
    var filter =
        new InvoiceSearchFilter(
            customerUuid, originRegionUuid, destRegionUuid, status, issueDateFrom, issueDateTo);
    return ResponseEntity.ok(invoiceService.search(filter, pageable));
  }
}
