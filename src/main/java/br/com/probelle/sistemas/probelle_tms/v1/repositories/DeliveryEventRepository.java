package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.DeliveryEvent;
import java.util.List;
import java.util.Optional;

public interface DeliveryEventRepository extends UuidRepository<DeliveryEvent> {



  List<DeliveryEvent> findByManifestIdOrderByOccurredAtAsc(Long manifestId);

  List<DeliveryEvent> findByInvoiceIdOrderByOccurredAtAsc(Long invoiceId);

  Optional<DeliveryEvent> findTopByManifestIdOrderByOccurredAtDesc(Long manifestId);
}
