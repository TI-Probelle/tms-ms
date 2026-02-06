package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.CarrierService;
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
@RequestMapping(value = "/api/carriers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarrierController {

  @Autowired private CarrierService carrierService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarrierResponseDTO> create(@RequestBody CarrierRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(carrierService.create(request));
  }

  @PutMapping(value = "/{carrierUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarrierResponseDTO> update(
      @PathVariable UUID carrierUuid, @RequestBody CarrierRequestDTO request) {
    return ResponseEntity.ok(carrierService.update(carrierUuid, request));
  }

  @GetMapping("/{carrierUuid}")
  public ResponseEntity<CarrierResponseDTO> getByUuid(@PathVariable UUID carrierUuid) {
    return ResponseEntity.ok(carrierService.getByUuid(carrierUuid));
  }

  @GetMapping
  public ResponseEntity<Page<CarrierResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(carrierService.list(pageable));
  }

  @GetMapping("/by-code")
  public ResponseEntity<CarrierResponseDTO> getByCode(@RequestParam String code) {
    return ResponseEntity.ok(carrierService.getByCode(code));
  }

  @DeleteMapping("/{carrierUuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID carrierUuid) {
    carrierService.delete(carrierUuid);
    return ResponseEntity.noContent().build();
  }
}
