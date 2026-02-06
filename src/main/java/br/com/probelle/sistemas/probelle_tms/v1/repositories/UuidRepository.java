package br.com.probelle.sistemas.probelle_tms.v1.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UuidRepository<E> extends JpaRepository<E, Long> {
  Optional<E> findByUuid(UUID uuid);

  boolean existsByUuid(UUID uuid);

  @Query("select e.id from #{#entityName} e where e.uuid = :uuid")
  Optional<Long> findIdByUuid(@Param("uuid") UUID uuid);

  void deleteByUuid(UUID uuid);
}
