package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.CustomerService;
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
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

  @Autowired private CustomerService customerService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request));
  }

  @PutMapping(value = "/{customerUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerResponseDTO> update(
      @PathVariable UUID customerUuid, @RequestBody CustomerRequestDTO request) {
    return ResponseEntity.ok(customerService.update(customerUuid, request));
  }

  @GetMapping("/{customerUuid}")
  public ResponseEntity<CustomerResponseDTO> getByUuid(@PathVariable UUID customerUuid) {
    return ResponseEntity.ok(customerService.getByUuid(customerUuid));
  }

  @GetMapping
  public ResponseEntity<Page<CustomerResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(customerService.list(pageable));
  }

  @GetMapping("/by-code")
  public ResponseEntity<CustomerResponseDTO> getByCode(@RequestParam String code) {
    return ResponseEntity.ok(customerService.getByCode(code));
  }

  @DeleteMapping("/{customerUuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID customerUuid) {
    customerService.delete(customerUuid);
    return ResponseEntity.noContent().build();
  }
}
