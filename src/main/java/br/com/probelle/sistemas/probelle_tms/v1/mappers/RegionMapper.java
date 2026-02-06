package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.region.RegionResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegionMapper
    extends BaseMapper<Region, RegionRequestDTO, RegionRequestDTO, RegionResponseDTO> {

  @Override
  Region toEntity(RegionRequestDTO dto);

  @Override
  RegionResponseDTO toResponse(Region entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Region updateEntity(RegionRequestDTO dto, @MappingTarget Region entity);
}
