package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.services.FreightRuleService;
import br.com.probelle.sistemas.probelle_tms.v1.services.FreightTableService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/freight-tables", produces = MediaType.APPLICATION_JSON_VALUE)
public class FreightTableController {

  @Autowired private FreightTableService freightTableService;

  @Autowired private FreightRuleService freightRuleService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FreightTableResponseDTO> create(
      @RequestBody FreightTableRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(freightTableService.create(request));
  }

  @GetMapping
  public ResponseEntity<?> list(
      @RequestParam(required = false) UUID carrierUuid,
      @RequestParam(required = false) Boolean active,
      Pageable pageable) {
    if (carrierUuid != null && (active == null || active)) {
      List<FreightTableResponseDTO> list = freightTableService.listActiveByCarrier(carrierUuid);
      return ResponseEntity.ok(list);
    }
    Page<FreightTableResponseDTO> page = freightTableService.list(pageable);
    return ResponseEntity.ok(page);
  }

  @PostMapping(value = "/{freightTableUuid}/rules", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FreightRuleResponseDTO> createRule(
      @PathVariable UUID freightTableUuid, @RequestBody FreightRuleRequestDTO request) {
    FreightRuleRequestDTO merged =
        new FreightRuleRequestDTO(
            freightTableUuid,
            request.originRegionUuid(),
            request.destRegionUuid(),
            request.weightBreakFrom(),
            request.weightBreakTo(),
            request.ratePerKg(),
            request.minimumFreight(),
            request.adValoremPct(),
            request.grisPct(),
            request.tollFixed(),
            request.dispatchFixed());
    return ResponseEntity.status(HttpStatus.CREATED).body(freightRuleService.create(merged));
  }
}
