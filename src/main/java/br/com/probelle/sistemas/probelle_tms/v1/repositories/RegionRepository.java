package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Region;
import java.util.Optional;

public interface RegionRepository extends UuidRepository<Region> {



  boolean existsByCode(String code);

  Optional<Region> findByCode(String code);
}
