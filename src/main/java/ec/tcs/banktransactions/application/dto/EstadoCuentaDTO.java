package ec.tcs.banktransactions.application.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EstadoCuentaDTO {

    private String numeroCuenta;

    private String cliente;

    private String tipoCuenta;

    private Double saldoInicial;

    private Double saldoActual;

    private List<MovimientoDTO> movimientos;

}
