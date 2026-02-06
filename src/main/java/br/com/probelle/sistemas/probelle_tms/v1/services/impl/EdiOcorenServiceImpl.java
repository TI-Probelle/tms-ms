package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.DeliveryEvent;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiDocument;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiRef;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiDirection;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiRefType;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiType;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.EdiDocumentMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.DeliveryEventRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.EdiDocumentRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.EdiRefRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.EdiOcorenService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.edi.OcorenEvent;
import br.com.probelle.sistemas.probelle_tms.v1.services.edi.OcorenParser;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.UploadOcorenRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EdiOcorenServiceImpl extends ServiceBase implements EdiOcorenService {

  @Autowired
  private CarrierRepository carrierRepository;

  @Autowired
  private ManifestRepository manifestRepository;

  @Autowired
  private EdiDocumentRepository ediDocumentRepository;

  @Autowired
  private EdiRefRepository ediRefRepository;

  @Autowired
  private DeliveryEventRepository deliveryEventRepository;

  @Autowired
  private EdiDocumentMapper ediDocumentMapper;

  private final OcorenParser ocorenParser = new OcorenParser();

  @Override
  @Transactional
  public EdiDocumentResponseDTO uploadOcoren(UploadOcorenRequest request) {
    var carrier = requireByUuid(carrierRepository, request.carrierUuid(), "Carrier");
    EdiDocument doc = new EdiDocument();
    doc.setCarrier(carrier);
    doc.setEdiType(EdiType.OCOREN);
    doc.setDirection(EdiDirection.INBOUND);
    doc.setStatus(EdiStatus.NEW);
    doc.setVersion(request.layoutVersion());
    doc.setFileName(resolveFileName(request.fileName()));
    doc.setReceivedAt(LocalDateTime.now());
    doc.setRawContent(request.rawContent());

    EdiDocument saved = ediDocumentRepository.save(doc);
    EdiDocument persisted = ediDocumentRepository.findById(saved.getId()).orElse(saved);

    try {
      applyOcoren(persisted);
      persisted.setStatus(EdiStatus.APPLIED);
      persisted = ediDocumentRepository.save(persisted);
    } catch (RuntimeException ex) {
      persisted.setStatus(EdiStatus.ERROR);
      ediDocumentRepository.save(persisted);
      throw ex;
    }

    return ediDocumentMapper.toResponse(persisted);
  }

  private void applyOcoren(EdiDocument ediDocument) {
    List<OcorenEvent> events = ocorenParser.parse(ediDocument.getRawContent());
    Map<String, Manifest> manifestCache = new HashMap<>();
    List<DeliveryEvent> deliveryEvents = new ArrayList<>();
    List<EdiRef> refs = new ArrayList<>();

    for (OcorenEvent event : events) {
      Manifest manifest = manifestCache.computeIfAbsent(
          event.manifestNo(),
          key ->
              manifestRepository
                  .findByManifestNo(key)
                  .orElseThrow(() -> new ValidationException("Manifest not found: " + key)));

      DeliveryEvent deliveryEvent = new DeliveryEvent();
      deliveryEvent.setManifest(manifest);
      deliveryEvent.setInvoice(null);
      deliveryEvent.setEdiDocument(ediDocument);
      deliveryEvent.setOccurrenceCode(event.code());
      deliveryEvent.setOccurrenceDesc(event.description() != null ? event.description() : "");
      deliveryEvent.setOccurredAt(event.occurredAt());
      deliveryEvent.setNote(null);
      deliveryEvents.add(deliveryEvent);

      EdiRef ref = new EdiRef();
      ref.setEdiDocument(ediDocument);
      ref.setRefType(EdiRefType.MANIFEST);
      ref.setRefId(manifest.getId());
      refs.add(ref);

      ManifestStatus newStatus = mapStatus(event.code());
      if (newStatus != null) {
        manifest.setStatus(newStatus);
      }
    }

    deliveryEventRepository.saveAll(deliveryEvents);
    ediRefRepository.saveAll(refs);
    manifestRepository.saveAll(manifestCache.values());
  }

  private ManifestStatus mapStatus(String code) {
    if (code == null) {
      return null;
    }
    String normalized = code.trim().toUpperCase(Locale.ROOT);
    return switch (normalized) {
      case "COLETADO" -> ManifestStatus.IN_TRANSIT;
      case "ENTREGUE" -> ManifestStatus.DELIVERED;
      case "PENDENTE", "INSUCESSO" -> ManifestStatus.PENDING;
      default -> null;
    };
  }

  private String resolveFileName(String fileName) {
    if (fileName != null && !fileName.isBlank()) {
      return fileName;
    }
    String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    return "OCOREN_" + stamp + ".txt";
  }
}
