package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestfreight.ManifestFreightResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestFreight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ManifestFreightMapper
    extends BaseMapper<ManifestFreight, ManifestFreightRequestDTO, ManifestFreightRequestDTO, ManifestFreightResponseDTO> {

  @Override
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "freightTable", ignore = true)
  ManifestFreight toEntity(ManifestFreightRequestDTO dto);

  @Override
  @Mapping(target = "manifestUuid", source = "manifest.uuid")
  @Mapping(target = "freightTableUuid", source = "freightTable.uuid")
  ManifestFreightResponseDTO toResponse(ManifestFreight entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "freightTable", ignore = true)
  ManifestFreight updateEntity(ManifestFreightRequestDTO dto, @MappingTarget ManifestFreight entity);
}
