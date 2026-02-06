package br.com.probelle.sistemas.probelle_tms.v1.repositories.projection;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import java.time.LocalDateTime;

public interface ManifestLockProjection {
  ManifestStatus getStatus();

  LocalDateTime getLockedAt();
}
