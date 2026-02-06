package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestinvoice.ManifestInvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestInvoice;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.ManifestFreightMapper;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.ManifestInvoiceMapper;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.ManifestMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CarrierRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CustomerRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.InvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestFreightRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestInvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.RegionRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.ManifestService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import br.com.probelle.sistemas.probelle_tms.v1.services.guards.ManifestGuards;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.AddInvoiceItem;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.AddInvoicesRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.LockRequest;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.ManifestDetailedResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManifestServiceImpl extends ServiceBase implements ManifestService {

  @Autowired
  private ManifestRepository manifestRepository;

  @Autowired
  private ManifestInvoiceRepository manifestInvoiceRepository;

  @Autowired
  private ManifestFreightRepository manifestFreightRepository;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CarrierRepository carrierRepository;

  @Autowired
  private RegionRepository regionRepository;

  @Autowired
  private ManifestMapper manifestMapper;

  @Autowired
  private ManifestInvoiceMapper manifestInvoiceMapper;

  @Autowired
  private ManifestFreightMapper manifestFreightMapper;

  @Override
  @Transactional
  public ManifestResponseDTO create(ManifestRequestDTO request) {
    String manifestNo = request.manifestNo();
    if (manifestNo == null || manifestNo.isBlank()) {
      manifestNo = generateManifestNo();
    }
    if (manifestRepository.existsByManifestNo(manifestNo)) {
      throw new ConflictException("Manifest number already exists");
    }
    Manifest manifest = manifestMapper.toEntity(request);
    manifest.setManifestNo(manifestNo);
    manifest.setCustomer(requireByUuid(customerRepository, request.customerUuid(), "Customer"));
    manifest.setCarrier(requireByUuid(carrierRepository, request.carrierUuid(), "Carrier"));
    manifest.setOriginRegion(requireByUuid(regionRepository, request.originRegionUuid(), "Region"));
    manifest.setDestRegion(requireByUuid(regionRepository, request.destRegionUuid(), "Region"));
    manifest.setStatus(ManifestStatus.OPEN);
    manifest.setLockedAt(null);
    manifest.setTotalWeightKg(BigDecimal.ZERO);
    manifest.setTotalValue(BigDecimal.ZERO);
    Manifest saved = manifestRepository.save(manifest);
    Manifest persisted = manifestRepository.findById(saved.getId()).orElse(saved);
    return manifestMapper.toResponse(persisted);
  }

  @Override
  @Transactional
  public ManifestDetailedResponse addInvoices(UUID manifestUuid, AddInvoicesRequest request) {
    Manifest manifest = requireByUuid(manifestRepository, manifestUuid, "Manifest");
    ManifestGuards.assertNotLocked(manifest);
    if (request == null || request.invoices() == null || request.invoices().isEmpty()) {
      throw new ValidationException("Invoices list is empty");
    }

    List<ManifestInvoice> links = request.invoices().stream()
        .map(item -> buildManifestInvoice(manifest, item))
        .collect(Collectors.toList());

    manifestInvoiceRepository.saveAll(links);

    recalcTotals(manifest);
    Manifest saved = manifestRepository.save(manifest);

    return buildDetailed(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public ManifestDetailedResponse getDetailed(UUID manifestUuid) {
    Manifest manifest =
        manifestRepository
            .findDetailedByUuid(manifestUuid)
            .orElseThrow(() -> new NotFoundException("Manifest not found"));
    return buildDetailed(manifest);
  }

  @Override
  @Transactional
  public ManifestResponseDTO lock(UUID manifestUuid, LockRequest request) {
    Manifest manifest = requireByUuid(manifestRepository, manifestUuid, "Manifest");
    ManifestGuards.assertNotLocked(manifest);
    ManifestGuards.assertHasInvoices(manifestInvoiceRepository, manifest.getId());
    ManifestGuards.assertHasFreight(manifestFreightRepository, manifest.getId());
    manifest.setLockedAt(LocalDateTime.now());
    manifest.setStatus(ManifestStatus.LOCKED);
    Manifest saved = manifestRepository.save(manifest);
    return manifestMapper.toResponse(saved);
  }

  private ManifestInvoice buildManifestInvoice(Manifest manifest, AddInvoiceItem item) {
    if (item == null || item.invoiceUuid() == null || item.seq() == null) {
      throw new ValidationException("Invalid invoice item");
    }
    var invoice = requireByUuid(invoiceRepository, item.invoiceUuid(), "Invoice");
    if (invoice.getStatus() == InvoiceStatus.CANCELLED) {
      throw new ConflictException("Cancelled invoice cannot be linked");
    }
    if (invoice.getStatus() != InvoiceStatus.READY) {
      throw new ConflictException("Invoice must be READY");
    }
    if (manifestInvoiceRepository.existsByManifestIdAndInvoiceId(manifest.getId(), invoice.getId())) {
      throw new ConflictException("Invoice already linked to manifest");
    }
    if (invoiceRepository.existsLinkedToManifestWithStatusNot(
        invoice.getId(), ManifestStatus.CANCELLED)) {
      throw new ConflictException("Invoice already linked to another active manifest");
    }
    ManifestInvoice link = new ManifestInvoice();
    link.setManifest(manifest);
    link.setInvoice(invoice);
    link.setSeq(item.seq());
    link.setWeightKg(invoice.getWeightKg());
    link.setValue(invoice.getNetValue());
    link.setLinkedAt(LocalDateTime.now());
    return link;
  }

  private void recalcTotals(Manifest manifest) {
    List<ManifestInvoice> items =
        manifestInvoiceRepository.findByManifestIdOrderBySeqAsc(manifest.getId());
    BigDecimal totalWeight = BigDecimal.ZERO;
    BigDecimal totalValue = BigDecimal.ZERO;
    for (ManifestInvoice item : items) {
      totalWeight = totalWeight.add(item.getWeightKg());
      totalValue = totalValue.add(item.getValue());
    }
    manifest.setTotalWeightKg(totalWeight);
    manifest.setTotalValue(totalValue);
  }

  private ManifestDetailedResponse buildDetailed(Manifest manifest) {
    List<ManifestInvoiceResponseDTO> invoices =
        manifestInvoiceRepository.findByManifestIdOrderBySeqAsc(manifest.getId()).stream()
            .map(manifestInvoiceMapper::toResponse)
            .collect(Collectors.toList());
    ManifestFreightResponseDTO freight =
        manifestFreightRepository
            .findByManifestId(manifest.getId())
            .map(manifestFreightMapper::toResponse)
            .orElse(null);
    return new ManifestDetailedResponse(manifestMapper.toResponse(manifest), invoices, freight);
  }

  private String generateManifestNo() {
    String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    return "ROM" + stamp;
  }
}
