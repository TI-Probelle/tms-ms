package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiDocument;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiRef;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiDirection;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiRefType;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiType;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.EdiDocumentMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.EdiDocumentRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.EdiRefRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestInvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.EdiNotfisService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.edi.NotfisSerializer;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import br.com.probelle.sistemas.probelle_tms.v1.services.guards.ManifestGuards;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.GenerateNotfisRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EdiNotfisServiceImpl extends ServiceBase implements EdiNotfisService {

  @Autowired
  private ManifestRepository manifestRepository;

  @Autowired
  private ManifestInvoiceRepository manifestInvoiceRepository;

  @Autowired
  private EdiDocumentRepository ediDocumentRepository;

  @Autowired
  private EdiRefRepository ediRefRepository;

  @Autowired
  private EdiDocumentMapper ediDocumentMapper;

  private final NotfisSerializer notfisSerializer = new NotfisSerializer();

  @Override
  @Transactional
  public EdiDocumentResponseDTO generateNotfis(GenerateNotfisRequest request) {
    var manifest =
        manifestRepository
            .findDetailedByUuid(request.manifestUuid())
            .orElseThrow(() -> new NotFoundException("Manifest not found"));
    ManifestGuards.assertLocked(manifest);

    var items = manifestInvoiceRepository.findByManifestIdOrderBySeqAsc(manifest.getId());

    String rawContent = notfisSerializer.serialize(manifest, items);
    EdiDocument doc = new EdiDocument();
    doc.setCarrier(manifest.getCarrier());
    doc.setEdiType(EdiType.NOTFIS);
    doc.setDirection(EdiDirection.OUTBOUND);
    doc.setStatus(EdiStatus.NEW);
    doc.setVersion(request.layoutVersion());
    doc.setFileName(buildFileName(manifest.getManifestNo()));
    doc.setGeneratedAt(LocalDateTime.now());
    doc.setRawContent(rawContent);

    EdiDocument saved = ediDocumentRepository.save(doc);
    EdiDocument persisted = ediDocumentRepository.findById(saved.getId()).orElse(saved);

    List<EdiRef> refs = new ArrayList<>();
    EdiRef manifestRef = new EdiRef();
    manifestRef.setEdiDocument(persisted);
    manifestRef.setRefType(EdiRefType.MANIFEST);
    manifestRef.setRefId(manifest.getId());
    refs.add(manifestRef);

    items.forEach(
        item -> {
          EdiRef ref = new EdiRef();
          ref.setEdiDocument(persisted);
          ref.setRefType(EdiRefType.INVOICE);
          ref.setRefId(item.getInvoice().getId());
          refs.add(ref);
        });

    ediRefRepository.saveAll(refs);

    return ediDocumentMapper.toResponse(persisted);
  }

  private String buildFileName(String manifestNo) {
    String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    return "NOTFIS_" + manifestNo + "_" + stamp + ".txt";
  }
}
