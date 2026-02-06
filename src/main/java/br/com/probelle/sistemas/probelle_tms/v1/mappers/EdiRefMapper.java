package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.ediref.EdiRefRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.ediref.EdiRefResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EdiRefMapper
    extends BaseMapper<EdiRef, EdiRefRequestDTO, EdiRefRequestDTO, EdiRefResponseDTO> {

  @Override
  @Mapping(target = "ediDocument", ignore = true)
  EdiRef toEntity(EdiRefRequestDTO dto);

  @Override
  @Mapping(target = "ediDocumentUuid", source = "ediDocument.uuid")
  EdiRefResponseDTO toResponse(EdiRef entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "ediDocument", ignore = true)
  EdiRef updateEntity(EdiRefRequestDTO dto, @MappingTarget EdiRef entity);
}
