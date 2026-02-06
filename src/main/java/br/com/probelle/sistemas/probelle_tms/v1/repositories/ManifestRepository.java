package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.projection.ManifestLockProjection;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.projection.ManifestTotalsProjection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManifestRepository extends UuidRepository<Manifest> {



  boolean existsByManifestNo(String manifestNo);

  @Query(
      """
      select m from Manifest m
      left join fetch m.customer
      left join fetch m.carrier
      left join fetch m.originRegion
      left join fetch m.destRegion
      where m.uuid = :uuid
      """)
  Optional<Manifest> findDetailedByUuid(@Param("uuid") UUID uuid);

  @Query("select m.status as status, m.lockedAt as lockedAt from Manifest m where m.uuid = :uuid")
  ManifestLockProjection findLockInfoByUuid(@Param("uuid") UUID uuid);

  @Query(
      "select m.totalWeightKg as totalWeightKg, m.totalValue as totalValue from Manifest m where"
          + " m.uuid = :uuid")
  ManifestTotalsProjection findTotalsByUuid(@Param("uuid") UUID uuid);
}
