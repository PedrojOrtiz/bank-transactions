package ec.tcs.banktransactions.application.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CuentaDTO {

    private Long id;

    private String numeroCuenta;

    private String tipoCuenta;

    private Double saldoInicial;

    private Boolean estado;

    private String clienteId;

    private List<MovimientoDTO> movimientos;

    private Integer version;

}
