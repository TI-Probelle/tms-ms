package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestInvoice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManifestInvoiceRepository extends UuidRepository<ManifestInvoice> {



  List<ManifestInvoice> findByManifestIdOrderBySeqAsc(Long manifestId);

  boolean existsByManifestIdAndInvoiceId(Long manifestId, Long invoiceId);

  @Query("select mi.invoice.id from ManifestInvoice mi where mi.manifest.id = :manifestId")
  List<Long> findInvoiceIdsByManifestId(@Param("manifestId") Long manifestId);

  long countByManifestId(Long manifestId);
}
