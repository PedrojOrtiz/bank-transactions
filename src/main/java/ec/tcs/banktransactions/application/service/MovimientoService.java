package ec.tcs.banktransactions.application.service;

import ec.tcs.banktransactions.application.dto.MovimientoDTO;
import ec.tcs.banktransactions.application.exception.InsufficientBalance;
import ec.tcs.banktransactions.application.mapper.CuentaMapper;
import ec.tcs.banktransactions.application.mapper.MovimientoMapper;
import ec.tcs.banktransactions.domain.model.Cuenta;
import ec.tcs.banktransactions.domain.model.Movimiento;
import ec.tcs.banktransactions.domain.repository.CuentaRepository;
import ec.tcs.banktransactions.domain.repository.MovimientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimientoService extends AbstractService<Movimiento, Long, MovimientoDTO, MovimientoRepository> {

    private Logger log = LoggerFactory.getLogger(MovimientoService.class);

    private final CuentaRepository cuentaRepository;

    public MovimientoService(MovimientoRepository repository, CuentaRepository cuentaRepository) {
        super(repository, LoggerFactory.getLogger(MovimientoService.class), "Movimiento");
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public MovimientoDTO mapToDTO(Movimiento entity) {
        return MovimientoMapper.INSTANCE.mapToDTO(entity);
    }

    @Override
    public Movimiento mapToEntity(MovimientoDTO movimientoDTO) {
        return MovimientoMapper.INSTANCE.mapToEntity(movimientoDTO);
    }

    @Transactional
    public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {

        /// Account validation
        Cuenta cuenta = cuentaRepository
                .findByNumeroCuenta(movimientoDTO.getNumeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        /// Balance validation
        double saldoActual = cuenta.getSaldoInicial() + repository.sumSaldoByCuentaId(cuenta.getId());
        double nuevoSaldo = saldoActual + movimientoDTO.getValor();

        if (nuevoSaldo < 0) {
            throw new InsufficientBalance("Saldo insuficiente");
        }

        /// Save transaction
        return save(
          MovimientoDTO.builder()
                  .fecha(movimientoDTO.getFecha())
                  .tipoMovimiento(movimientoDTO.getTipoMovimiento())
                  .valor(movimientoDTO.getValor())
                  .saldo(nuevoSaldo)
                  .cuenta(CuentaMapper.INSTANCE.mapToDTO(cuenta))
                  .build()
        );

    }

}
