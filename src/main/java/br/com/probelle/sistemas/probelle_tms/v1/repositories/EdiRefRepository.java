package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiRef;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiRefType;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EdiRefRepository extends UuidRepository<EdiRef> {



  List<EdiRef> findByEdiDocumentId(Long ediDocumentId);

  @Query("select e from EdiRef e where e.refType = :refType and e.refId = :refId")
  List<EdiRef> findByRefTypeAndRefId(@Param("refType") EdiRefType refType, @Param("refId") Long refId);
}
