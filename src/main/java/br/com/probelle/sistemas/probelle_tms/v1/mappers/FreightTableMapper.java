package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freighttable.FreightTableResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FreightTableMapper
    extends BaseMapper<FreightTable, FreightTableRequestDTO, FreightTableRequestDTO, FreightTableResponseDTO> {

  @Override
  @Mapping(target = "carrier", ignore = true)
  FreightTable toEntity(FreightTableRequestDTO dto);

  @Override
  @Mapping(target = "carrierUuid", source = "carrier.uuid")
  FreightTableResponseDTO toResponse(FreightTable entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "carrier", ignore = true)
  FreightTable updateEntity(FreightTableRequestDTO dto, @MappingTarget FreightTable entity);
}
