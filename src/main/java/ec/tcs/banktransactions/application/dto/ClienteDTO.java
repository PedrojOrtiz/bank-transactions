package ec.tcs.banktransactions.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class ClienteDTO extends PersonaDTO{

    private String clienteId;

    private String contrasena;

    private Boolean estado;

}
