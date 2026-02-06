package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestinvoice.ManifestInvoiceRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.manifestinvoice.ManifestInvoiceResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestInvoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ManifestInvoiceMapper
    extends BaseMapper<
        ManifestInvoice,
        ManifestInvoiceRequestDTO,
        ManifestInvoiceRequestDTO,
        ManifestInvoiceResponseDTO> {

  @Override
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "invoice", ignore = true)
  @Mapping(target = "linkedAt", ignore = true)
  ManifestInvoice toEntity(ManifestInvoiceRequestDTO dto);

  @Override
  @Mapping(target = "manifestUuid", source = "manifest.uuid")
  @Mapping(target = "invoiceUuid", source = "invoice.uuid")
  ManifestInvoiceResponseDTO toResponse(ManifestInvoice entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "linkedAt", ignore = true)
  @Mapping(target = "manifest", ignore = true)
  @Mapping(target = "invoice", ignore = true)
  ManifestInvoice updateEntity(
      ManifestInvoiceRequestDTO dto, @MappingTarget ManifestInvoice entity);
}
