package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Invoice;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.InvoiceMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CustomerRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.InvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.RegionRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.InvoiceService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.model.InvoiceSearchFilter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl extends ServiceBase implements InvoiceService {

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private RegionRepository regionRepository;

  @Autowired
  private InvoiceMapper invoiceMapper;

  @Override
  @Transactional
  public InvoiceResponseDTO create(InvoiceRequestDTO request) {
    if (invoiceRepository.existsByNfKey(request.nfKey())) {
      throw new ConflictException("NF-e key already exists");
    }
    Invoice invoice = invoiceMapper.toEntity(request);
    invoice.setCustomer(requireByUuid(customerRepository, request.customerUuid(), "Customer"));
    invoice.setOriginRegion(requireByUuid(regionRepository, request.originRegionUuid(), "Region"));
    invoice.setDestRegion(requireByUuid(regionRepository, request.destRegionUuid(), "Region"));
    if (invoice.getStatus() == null) {
      invoice.setStatus(InvoiceStatus.DRAFT);
    }
    Invoice saved = invoiceRepository.save(invoice);
    Invoice persisted = invoiceRepository.findById(saved.getId()).orElse(saved);
    return invoiceMapper.toResponse(persisted);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InvoiceResponseDTO> search(InvoiceSearchFilter filter, Pageable pageable) {
    Long customerId = null;
    Long originId = null;
    Long destId = null;
    if (filter.customerUuid() != null) {
      customerId = requireIdByUuid(customerRepository, filter.customerUuid(), "Customer");
    }
    if (filter.originRegionUuid() != null) {
      originId = requireIdByUuid(regionRepository, filter.originRegionUuid(), "Region");
    }
    if (filter.destRegionUuid() != null) {
      destId = requireIdByUuid(regionRepository, filter.destRegionUuid(), "Region");
    }
    return invoiceRepository
        .findByFilters(
            customerId,
            originId,
            destId,
            filter.status(),
            filter.issueDateFrom(),
            filter.issueDateTo(),
            pageable)
        .map(invoiceMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public InvoiceResponseDTO getByUuid(UUID uuid) {
    Invoice invoice = requireByUuid(invoiceRepository, uuid, "Invoice");
    return invoiceMapper.toResponse(invoice);
  }
}
