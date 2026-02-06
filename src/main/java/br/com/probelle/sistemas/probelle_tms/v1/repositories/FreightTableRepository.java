package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightTable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreightTableRepository extends UuidRepository<FreightTable> {



  List<FreightTable> findByCarrierIdAndActiveTrueOrderByValidFromDesc(Long carrierId);

  @Query(
      """
      select ft from FreightTable ft
      where ft.carrier.id = :carrierId
        and ft.active = true
        and :refDate between ft.validFrom and ft.validTo
      order by ft.validFrom desc
      """)
  Optional<FreightTable> findActiveForCarrierOnDate(
      @Param("carrierId") Long carrierId, @Param("refDate") LocalDate refDate);
}
