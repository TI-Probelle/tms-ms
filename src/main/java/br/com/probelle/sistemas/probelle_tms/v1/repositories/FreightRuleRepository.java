package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightRule;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreightRuleRepository extends UuidRepository<FreightRule> {



  @Query(
      """
      select fr from FreightRule fr
      where fr.freightTable.id = :freightTableId
        and fr.originRegion.id = :originId
        and fr.destRegion.id = :destId
        and :weight between fr.weightBreakFrom and fr.weightBreakTo
      order by fr.weightBreakFrom desc
      """)
  Optional<FreightRule> findRuleForWeight(
      @Param("freightTableId") Long freightTableId,
      @Param("originId") Long originId,
      @Param("destId") Long destId,
      @Param("weight") BigDecimal weight);
}
