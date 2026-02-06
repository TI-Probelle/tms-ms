package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.edidocument.EdiDocumentResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.EdiDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EdiDocumentMapper
    extends BaseMapper<EdiDocument, EdiDocumentRequestDTO, EdiDocumentRequestDTO, EdiDocumentResponseDTO> {

  @Override
  @Mapping(target = "carrier", ignore = true)
  EdiDocument toEntity(EdiDocumentRequestDTO dto);

  @Override
  @Mapping(target = "carrierUuid", source = "carrier.uuid")
  EdiDocumentResponseDTO toResponse(EdiDocument entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "carrier", ignore = true)
  EdiDocument updateEntity(EdiDocumentRequestDTO dto, @MappingTarget EdiDocument entity);
}
