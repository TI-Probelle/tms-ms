package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.deliveryevent.DeliveryEventRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.deliveryevent.DeliveryEventResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.DeliveryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryEventMapper
    extends BaseMapper<DeliveryEvent, DeliveryEventRequestDTO, DeliveryEventRequestDTO, DeliveryEventResponseDTO> {

  @Override
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "invoice", ignore = true)
  @Mapping(target = "ediDocument", ignore = true)
  DeliveryEvent toEntity(DeliveryEventRequestDTO dto);

  @Override
  @Mapping(target = "manifestUuid", source = "manifest.uuid")
  @Mapping(target = "invoiceUuid", source = "invoice.uuid")
  @Mapping(target = "ediDocumentUuid", source = "ediDocument.uuid")
  DeliveryEventResponseDTO toResponse(DeliveryEvent entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "invoice", ignore = true)
  @Mapping(target = "ediDocument", ignore = true)
  DeliveryEvent updateEntity(DeliveryEventRequestDTO dto, @MappingTarget DeliveryEvent entity);
}
