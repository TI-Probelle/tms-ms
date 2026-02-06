package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Customer;
import java.util.Optional;

public interface CustomerRepository extends UuidRepository<Customer> {



  Optional<Customer> findByCode(String code);

  boolean existsByCode(String code);
}
