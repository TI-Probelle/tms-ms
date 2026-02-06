package br.com.probelle.sistemas.probelle_tms.v1.repositories.projection;

import java.math.BigDecimal;

public interface ManifestTotalsProjection {
  BigDecimal getTotalWeightKg();

  BigDecimal getTotalValue();
}
