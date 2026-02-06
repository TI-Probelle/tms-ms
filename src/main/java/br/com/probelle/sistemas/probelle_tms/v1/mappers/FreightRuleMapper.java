package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.freightrule.FreightRuleResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.FreightRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FreightRuleMapper
    extends BaseMapper<FreightRule, FreightRuleRequestDTO, FreightRuleRequestDTO, FreightRuleResponseDTO> {

  @Override
  @Mapping(target = "freightTable", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  FreightRule toEntity(FreightRuleRequestDTO dto);

  @Override
  @Mapping(target = "freightTableUuid", source = "freightTable.uuid")
  @Mapping(target = "originRegionUuid", source = "originRegion.uuid")
  @Mapping(target = "destRegionUuid", source = "destRegion.uuid")
  FreightRuleResponseDTO toResponse(FreightRule entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "freightTable", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  FreightRule updateEntity(FreightRuleRequestDTO dto, @MappingTarget FreightRule entity);
}
