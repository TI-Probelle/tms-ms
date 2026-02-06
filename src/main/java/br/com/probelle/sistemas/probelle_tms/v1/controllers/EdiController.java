package br.com.probelle.sistemas.probelle_tms.v1.controllers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.EdiDocumentMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.EdiDocumentRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.EdiNotfisService;
import br.com.probelle.sistemas.probelle_tms.v1.services.EdiOcorenService;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.GenerateNotfisRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.UploadOcorenRequest;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/edi", produces = MediaType.APPLICATION_JSON_VALUE)
public class EdiController {

  @Autowired private EdiNotfisService ediNotfisService;

  @Autowired private EdiOcorenService ediOcorenService;

  @Autowired private EdiDocumentRepository ediDocumentRepository;

  @Autowired private EdiDocumentMapper ediDocumentMapper;

  @PostMapping(value = "/notfis", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EdiDocumentResponseDTO> generateNotfis(
      @org.springframework.web.bind.annotation.RequestBody GenerateNotfisRequest request) {
    return ResponseEntity.ok(ediNotfisService.generateNotfis(request));
  }

  @PostMapping(value = "/ocoren/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<EdiDocumentResponseDTO> uploadOcoren(
      @RequestParam UUID carrierUuid,
      @RequestParam(required = false) String layoutVersion,
      @RequestPart("file") MultipartFile file) {
    String rawContent;
    try {
      rawContent = new String(file.getBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new RuntimeException("Failed to read file", ex);
    }
    UploadOcorenRequest request =
        new UploadOcorenRequest(carrierUuid, layoutVersion, file.getOriginalFilename(), rawContent);
    EdiDocumentResponseDTO response = ediOcorenService.uploadOcoren(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/documents/{ediUuid}")
  public ResponseEntity<EdiDocumentResponseDTO> getByUuid(@PathVariable UUID ediUuid) {
    var doc =
        ediDocumentRepository
            .findByUuid(ediUuid)
            .orElseThrow(() -> new NotFoundException("EDI document not found"));
    return ResponseEntity.ok(ediDocumentMapper.toResponse(doc));
  }
}
