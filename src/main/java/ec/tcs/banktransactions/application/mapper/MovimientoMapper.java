package ec.tcs.banktransactions.application.mapper;

import ec.tcs.banktransactions.application.dto.MovimientoDTO;
import ec.tcs.banktransactions.domain.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    MovimientoDTO mapToDTO(Movimiento movimiento);

    Movimiento mapToEntity(MovimientoDTO movimientoDTO);

}
