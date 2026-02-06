package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Carrier;
import java.util.Optional;

public interface CarrierRepository extends UuidRepository<Carrier> {



  Optional<Carrier> findByCode(String code);

  boolean existsByCode(String code);
}
