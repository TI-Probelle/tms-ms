package br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable;

import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.FreightTableMode;
import java.time.LocalDate;
import java.util.UUID;

public record FreightTableRequestDTO(
    UUID carrierUuid,
    String name,
    FreightTableMode mode,
    LocalDate validFrom,
    LocalDate validTo,
    Boolean active) {}
