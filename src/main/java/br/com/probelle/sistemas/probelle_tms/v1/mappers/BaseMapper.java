package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, R, U, S> {
  E toEntity(R dto);

  S toResponse(E entity);

  E updateEntity(U dto, @MappingTarget E entity);
}
