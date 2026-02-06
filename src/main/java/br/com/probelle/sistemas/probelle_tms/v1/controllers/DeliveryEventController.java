package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.deliveryevent.DeliveryEventResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.DeliveryEventMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.DeliveryEventRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.InvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryEventController {

  @Autowired private ManifestRepository manifestRepository;

  @Autowired private InvoiceRepository invoiceRepository;

  @Autowired private DeliveryEventRepository deliveryEventRepository;

  @Autowired private DeliveryEventMapper deliveryEventMapper;

  @GetMapping("/manifests/{manifestUuid}/events")
  public ResponseEntity<List<DeliveryEventResponseDTO>> listByManifest(
      @PathVariable UUID manifestUuid) {
    Long manifestId =
        manifestRepository
            .findIdByUuid(manifestUuid)
            .orElseThrow(() -> new NotFoundException("Manifest not found"));
    List<DeliveryEventResponseDTO> list =
        deliveryEventRepository.findByManifestIdOrderByOccurredAtAsc(manifestId).stream()
            .map(deliveryEventMapper::toResponse)
            .collect(Collectors.toList());
    return ResponseEntity.ok(list);
  }

  @GetMapping("/invoices/{invoiceUuid}/events")
  public ResponseEntity<List<DeliveryEventResponseDTO>> listByInvoice(
      @PathVariable UUID invoiceUuid) {
    Long invoiceId =
        invoiceRepository
            .findIdByUuid(invoiceUuid)
            .orElseThrow(() -> new NotFoundException("Invoice not found"));
    List<DeliveryEventResponseDTO> list =
        deliveryEventRepository.findByInvoiceIdOrderByOccurredAtAsc(invoiceId).stream()
            .map(deliveryEventMapper::toResponse)
            .collect(Collectors.toList());
    return ResponseEntity.ok(list);
  }
}
