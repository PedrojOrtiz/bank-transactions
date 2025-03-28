package ec.tcs.banktransactions.application.service;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.application.mapper.CuentaMapper;
import ec.tcs.banktransactions.domain.model.Cuenta;
import ec.tcs.banktransactions.domain.repository.CuentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CuentaService extends AbstractService<Cuenta, Long, CuentaDTO, CuentaRepository> {

    private Logger log = LoggerFactory.getLogger(CuentaService.class);

    public CuentaService(CuentaRepository repository) {
        super(repository, LoggerFactory.getLogger(CuentaService.class), "Cuenta");
    }

    @Override
    public CuentaDTO mapToDTO(Cuenta entity) {
        return CuentaMapper.INSTANCE.mapToDTO(entity);
    }

    @Override
    public Cuenta mapToEntity(CuentaDTO cuentaDTO) {
        return CuentaMapper.INSTANCE.mapToEntity(cuentaDTO);
    }

    public CuentaDTO createNewDefaultAccount(CuentaDTO cuentaDTO) {
        return save(cuentaDTO);
    }

    public CuentaDTO updateCuenta(Long id, Cuenta cuenta) throws Throwable {
        CuentaDTO cuentaToUpdate = read(id);
        return update(
                cuentaToUpdate.getId(),
                CuentaDTO.builder()
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .tipoCuenta(cuenta.getTipoCuenta())
                        .estado(cuenta.getEstado())
                        .clienteId(cuenta.getClienteId())
                        .build()
        );
    }

}
