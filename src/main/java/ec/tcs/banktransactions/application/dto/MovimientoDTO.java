package ec.tcs.banktransactions.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MovimientoDTO {

    private Long id;

    private LocalDate fecha;

    private String cliente;

    private String numeroCuenta;

    private String tipoCuenta;

    private Double saldoInicial;

    private String tipoMovimiento;

    private Boolean estado;

    private Double valor;

    private Double saldo;

    private CuentaDTO cuenta;

}
