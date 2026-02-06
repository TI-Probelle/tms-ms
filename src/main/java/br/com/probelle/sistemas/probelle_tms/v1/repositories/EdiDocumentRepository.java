package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiDocument;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiDirection;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiType;
import java.util.List;

public interface EdiDocumentRepository extends UuidRepository<EdiDocument> {



  List<EdiDocument> findByCarrierIdAndEdiTypeAndDirectionOrderByCreatedAtDesc(
      Long carrierId, EdiType ediType, EdiDirection direction);

  List<EdiDocument> findByStatusOrderByCreatedAtDesc(EdiStatus status);
}
