package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.repositories.UuidRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import java.util.Optional;
import java.util.UUID;

public abstract class ServiceBase {

  protected <E> E requireByUuid(UuidRepository<E> repository, UUID uuid, String name) {
    return repository
        .findByUuid(uuid)
        .orElseThrow(() -> new NotFoundException(name + " not found"));
  }

  protected <E> Long requireIdByUuid(UuidRepository<E> repository, UUID uuid, String name) {
    return repository
        .findIdByUuid(uuid)
        .orElseThrow(() -> new NotFoundException(name + " not found"));
  }

  protected <E> E requireById(Optional<E> entity, String name) {
    return entity.orElseThrow(() -> new NotFoundException(name + " not found"));
  }
}
