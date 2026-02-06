package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.invoice.InvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper
    extends BaseMapper<Invoice, InvoiceRequestDTO, InvoiceRequestDTO, InvoiceResponseDTO> {

  @Override
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  Invoice toEntity(InvoiceRequestDTO dto);

  @Override
  @Mapping(target = "customerUuid", source = "customer.uuid")
  @Mapping(target = "originRegionUuid", source = "originRegion.uuid")
  @Mapping(target = "destRegionUuid", source = "destRegion.uuid")
  InvoiceResponseDTO toResponse(Invoice entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "originRegion", ignore = true)
  @Mapping(target = "destRegion", ignore = true)
  Invoice updateEntity(InvoiceRequestDTO dto, @MappingTarget Invoice entity);
}
