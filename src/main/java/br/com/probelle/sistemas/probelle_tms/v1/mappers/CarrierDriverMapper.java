package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrierdriver.CarrierDriverResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.CarrierDriver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarrierDriverMapper
    extends BaseMapper<CarrierDriver, CarrierDriverRequestDTO, CarrierDriverRequestDTO, CarrierDriverResponseDTO> {

  @Override
  @Mapping(target = "carrier", ignore = true)
  CarrierDriver toEntity(CarrierDriverRequestDTO dto);

  @Override
  @Mapping(target = "carrierUuid", source = "carrier.uuid")
  CarrierDriverResponseDTO toResponse(CarrierDriver entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "carrier", ignore = true)
  CarrierDriver updateEntity(CarrierDriverRequestDTO dto, @MappingTarget CarrierDriver entity);
}
