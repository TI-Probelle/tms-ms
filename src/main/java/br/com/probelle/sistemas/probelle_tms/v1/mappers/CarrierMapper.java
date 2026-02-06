package br.com.probelle.sistemas.probelle_tms.v1.mappers;

import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.carrier.CarrierResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Carrier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarrierMapper
    extends BaseMapper<Carrier, CarrierRequestDTO, CarrierRequestDTO, CarrierResponseDTO> {

  @Override
  Carrier toEntity(CarrierRequestDTO dto);

  @Override
  CarrierResponseDTO toResponse(Carrier entity);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Carrier updateEntity(CarrierRequestDTO dto, @MappingTarget Carrier entity);
}
