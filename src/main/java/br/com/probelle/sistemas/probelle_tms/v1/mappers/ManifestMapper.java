package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifest.ManifestResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ManifestMapper
    extends BaseMapper<Manifest, ManifestRequestDTO, ManifestRequestDTO, ManifestResponseDTO> {

  @Override
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "carrier", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  Manifest toEntity(ManifestRequestDTO dto);

  @Override
  @Mapping(target = "customerUuid", source = "customer.uuid")
  @Mapping(target = "carrierUuid", source = "carrier.uuid")
  @Mapping(target = "originRegionUuid", source = "originRegion.uuid")
  @Mapping(target = "destRegionUuid", source = "destRegion.uuid")
  ManifestResponseDTO toResponse(Manifest entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "carrier", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  Manifest updateEntity(ManifestRequestDTO dto, @MappingTarget Manifest entity);
}
