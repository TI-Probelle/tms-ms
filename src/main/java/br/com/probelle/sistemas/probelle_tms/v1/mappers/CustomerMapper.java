package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper
    extends BaseMapper<Customer, CustomerRequestDTO, CustomerRequestDTO, CustomerResponseDTO> {

  @Override
  @Mapping(target = "defaultRegion", ignore = true)
  Customer toEntity(CustomerRequestDTO dto);

  @Override
  @Mapping(target = "defaultRegionUuid", source = "defaultRegion.uuid")
  CustomerResponseDTO toResponse(Customer entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "defaultRegion", ignore = true)
  Customer updateEntity(CustomerRequestDTO dto, @MappingTarget Customer entity);
}
