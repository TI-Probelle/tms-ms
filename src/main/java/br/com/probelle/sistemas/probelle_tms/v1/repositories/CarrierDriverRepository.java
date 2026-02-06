package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import br.com.probelle.sistemas.probelle_tms.v1.entity.CarrierDriver;
import java.util.List;

public interface CarrierDriverRepository extends UuidRepository<CarrierDriver> {



  List<CarrierDriver> findByCarrierIdAndActiveTrueOrderByNameAsc(Long carrierId);
}
