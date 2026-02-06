package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestFreight;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManifestFreightRepository extends UuidRepository<ManifestFreight> {



  Optional<ManifestFreight> findByManifestId(Long manifestId);

  boolean existsByManifestId(Long manifestId);

  @Query("select mf from ManifestFreight mf where mf.manifest.uuid = :manifestUuid")
  Optional<ManifestFreight> findByManifestUuid(@Param("manifestUuid") UUID manifestUuid);
}
