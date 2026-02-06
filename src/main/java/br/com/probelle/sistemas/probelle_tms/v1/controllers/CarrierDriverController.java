package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.CarrierDriverService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/carrier-drivers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarrierDriverController {

  @Autowired private CarrierDriverService carrierDriverService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarrierDriverResponseDTO> create(
      @RequestBody CarrierDriverRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(carrierDriverService.create(request));
  }

  @PutMapping(value = "/{driverUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarrierDriverResponseDTO> update(
      @PathVariable UUID driverUuid, @RequestBody CarrierDriverRequestDTO request) {
    return ResponseEntity.ok(carrierDriverService.update(driverUuid, request));
  }

  @GetMapping("/{driverUuid}")
  public ResponseEntity<CarrierDriverResponseDTO> getByUuid(@PathVariable UUID driverUuid) {
    return ResponseEntity.ok(carrierDriverService.getByUuid(driverUuid));
  }

  @GetMapping
  public ResponseEntity<List<CarrierDriverResponseDTO>> listActiveByCarrier(
      @RequestParam UUID carrierUuid) {
    return ResponseEntity.ok(carrierDriverService.listActiveByCarrier(carrierUuid));
  }

  @DeleteMapping("/{driverUuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID driverUuid) {
    carrierDriverService.delete(driverUuid);
    return ResponseEntity.noContent().build();
  }
}
