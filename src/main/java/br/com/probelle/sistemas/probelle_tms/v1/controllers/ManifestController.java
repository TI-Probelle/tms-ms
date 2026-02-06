package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.ManifestMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.ManifestService;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.AddInvoicesRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.LockRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestDetailedResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/manifests", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManifestController {

  @Autowired private ManifestService manifestService;

  @Autowired private ManifestRepository manifestRepository;

  @Autowired private ManifestMapper manifestMapper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ManifestResponseDTO> create(@RequestBody ManifestRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manifestService.create(request));
  }

  @GetMapping("/{manifestUuid}")
  public ResponseEntity<ManifestDetailedResponse> getDetailed(@PathVariable UUID manifestUuid) {
    return ResponseEntity.ok(manifestService.getDetailed(manifestUuid));
  }

  @GetMapping
  public ResponseEntity<Page<ManifestResponseDTO>> list(Pageable pageable) {
    Page<ManifestResponseDTO> page =
        manifestRepository.findAll(pageable).map(manifestMapper::toResponse);
    return ResponseEntity.ok(page);
  }

  @PostMapping(value = "/{manifestUuid}/invoices", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ManifestDetailedResponse> addInvoices(
      @PathVariable UUID manifestUuid, @RequestBody AddInvoicesRequest request) {
    return ResponseEntity.ok(manifestService.addInvoices(manifestUuid, request));
  }

  @PostMapping(value = "/{manifestUuid}/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ManifestResponseDTO> lock(
      @PathVariable UUID manifestUuid, @RequestBody(required = false) LockRequest request) {
    LockRequest resolved = request != null ? request : new LockRequest(null);
    return ResponseEntity.ok(manifestService.lock(manifestUuid, resolved));
  }
}
