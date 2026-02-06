package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Invoice;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends UuidRepository<Invoice> {



  boolean existsByNfKey(String nfKey);

  List<Invoice> findByCustomerIdAndStatusOrderByIssueDateDesc(
      Long customerId, InvoiceStatus status);

  @Query(
      """
      select i from Invoice i
      where (:customerId is null or i.customer.id = :customerId)
        and (:originRegionId is null or i.originRegion.id = :originRegionId)
        and (:destRegionId is null or i.destRegion.id = :destRegionId)
        and (:status is null or i.status = :status)
        and (:issueDateFrom is null or i.issueDate >= :issueDateFrom)
        and (:issueDateTo is null or i.issueDate <= :issueDateTo)
      """)
  Page<Invoice> findByFilters(
      @Param("customerId") Long customerId,
      @Param("originRegionId") Long originRegionId,
      @Param("destRegionId") Long destRegionId,
      @Param("status") InvoiceStatus status,
      @Param("issueDateFrom") LocalDate issueDateFrom,
      @Param("issueDateTo") LocalDate issueDateTo,
      Pageable pageable);

  @Query(
      """
      select (count(mi) > 0) from ManifestInvoice mi
      where mi.invoice.id = :invoiceId
        and mi.manifest.status <> :status
      """)
  boolean existsLinkedToManifestWithStatusNot(
      @Param("invoiceId") Long invoiceId, @Param("status") ManifestStatus status);
}
