package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.PricingService;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestFreightPriceRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/manifests", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManifestFreightController {

  @Autowired private PricingService pricingService;

  @PostMapping(value = "/{manifestUuid}/freight/price", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ManifestFreightResponseDTO> price(
      @PathVariable UUID manifestUuid, @RequestBody ManifestFreightPriceRequest request) {
    return ResponseEntity.ok(pricingService.price(manifestUuid, request));
  }
}
