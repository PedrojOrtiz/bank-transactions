package ec.tcs.banktransactions.application.mapper;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.domain.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    @Mapping(source = "movimientos", target = "movimientos")
    CuentaDTO mapToDTO(Cuenta cuenta);

    @Mapping(source = "movimientos", target = "movimientos")
    Cuenta mapToEntity(CuentaDTO cuentaDTO);

}
