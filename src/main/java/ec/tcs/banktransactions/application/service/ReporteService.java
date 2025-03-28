package ec.tcs.banktransactions.application.service;

import ec.tcs.banktransactions.application.dto.ClienteDTO;
import ec.tcs.banktransactions.application.dto.EstadoCuentaDTO;
import ec.tcs.banktransactions.application.dto.MovimientoDTO;
import ec.tcs.banktransactions.application.exception.DataNotFound;
import ec.tcs.banktransactions.domain.model.Cuenta;
import ec.tcs.banktransactions.domain.repository.CuentaRepository;
import ec.tcs.banktransactions.domain.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    Logger log = Logger.getLogger(ReporteService.class.getName());

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;

    @Transactional
    public List<EstadoCuentaDTO> generarReporte(LocalDate fechaInicial, LocalDate fechaFinal, String... clienteId) {

        log.info("Generando reporte");

        List<Cuenta> cuentaList;

        if (clienteId == null || clienteId.length == 0) {
            cuentaList = cuentaRepository.findAll();
            if (cuentaList == null || cuentaList.isEmpty()) {
                throw new DataNotFound("No se encontraron cuentas");
            }
        } else {
            cuentaList = cuentaRepository.findByClienteId(clienteId[0]);
            if (cuentaList == null || cuentaList.isEmpty()) {
                throw new DataNotFound("No se encontraron cuentas para el cliente");
            }
        }

        return cuentaList
                .stream()
                .map(cuenta -> {

                    ClienteDTO clienteDTO = clienteService
                            .getClientById(cuenta.getClienteId())
                            .orElseThrow(() -> new DataNotFound("Información del cliente: " + cuenta.getClienteId() + " no encontrada"));

                    List<MovimientoDTO> movimientoDTOList = movimientoRepository
                            .findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicial, fechaFinal)
                            .stream()
                            .map(mov ->
                                    MovimientoDTO.builder()
                                            .fecha(mov.getFecha())
                                            .cliente(clienteDTO.getNombre())
                                            .numeroCuenta(cuenta.getNumeroCuenta())
                                            .tipoCuenta(cuenta.getTipoCuenta())
                                            .tipoMovimiento(mov.getTipoMovimiento())
                                            .saldoInicial(mov.getSaldo() - mov.getValor())
                                            .valor(mov.getValor())
                                            .saldo(mov.getSaldo())
                                            .build()
                            )
                            .collect(Collectors.toList());

                    return EstadoCuentaDTO.builder()
                            .numeroCuenta(cuenta.getNumeroCuenta())
                            .cliente(clienteDTO.getNombre())
                            .tipoCuenta(cuenta.getTipoCuenta())
                            .saldoInicial(cuenta.getSaldoInicial())
                            .saldoActual(
                                    cuenta.getSaldoInicial() + movimientoRepository.sumSaldoByCuentaId(cuenta.getId())
                            )
                            .movimientos(movimientoDTOList)
                            .build();
                }).collect(Collectors.toList());

    }

}
