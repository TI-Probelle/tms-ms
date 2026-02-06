package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.RegionService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/regions", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController {

  @Autowired private RegionService regionService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegionResponseDTO> create(@RequestBody RegionRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(regionService.create(request));
  }

  @PutMapping(value = "/{regionUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegionResponseDTO> update(
      @PathVariable UUID regionUuid, @RequestBody RegionRequestDTO request) {
    return ResponseEntity.ok(regionService.update(regionUuid, request));
  }

  @GetMapping("/{regionUuid}")
  public ResponseEntity<RegionResponseDTO> getByUuid(@PathVariable UUID regionUuid) {
    return ResponseEntity.ok(regionService.getByUuid(regionUuid));
  }

  @GetMapping
  public ResponseEntity<Page<RegionResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(regionService.list(pageable));
  }

  @GetMapping("/by-code")
  public ResponseEntity<RegionResponseDTO> getByCode(@RequestParam String code) {
    return ResponseEntity.ok(regionService.getByCode(code));
  }

  @DeleteMapping("/{regionUuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID regionUuid) {
    regionService.delete(regionUuid);
    return ResponseEntity.noContent().build();
  }
}
