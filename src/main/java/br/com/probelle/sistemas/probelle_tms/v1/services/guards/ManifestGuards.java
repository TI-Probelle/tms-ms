package br.com.probelle.sistemas.probelle_tms.v1.services.guards;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestFreightRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.ManifestInvoiceRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import java.math.BigDecimal;

public final class ManifestGuards {

  private ManifestGuards() {}

  public static void assertNotLocked(Manifest manifest) {
    if (manifest.getLockedAt() != null || manifest.getStatus() == ManifestStatus.LOCKED) {
      throw new ConflictException("Manifest is locked");
    }
  }

  public static void assertLocked(Manifest manifest) {
    if (manifest.getLockedAt() == null && manifest.getStatus() != ManifestStatus.LOCKED) {
      throw new ConflictException("Manifest is not locked");
    }
  }

  public static void assertHasInvoices(
      ManifestInvoiceRepository repository, Long manifestId) {
    if (repository.countByManifestId(manifestId) <= 0) {
      throw new ConflictException("Manifest has no invoices");
    }
  }

  public static void assertHasFreight(
      ManifestFreightRepository repository, Long manifestId) {
    var freight = repository.findByManifestId(manifestId).orElse(null);
    if (freight == null
        || freight.getFreightTotal() == null
        || freight.getFreightTotal().compareTo(BigDecimal.ZERO) <= 0) {
      throw new ConflictException("Manifest has no freight");
    }
  }
}
