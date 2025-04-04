package ec.tcs.banktransactions.infrastructure.event;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.application.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ClienteConsumer {

    private final Logger log = LoggerFactory.getLogger(ClienteConsumer.class);

    private final CuentaService cuentaService;

    @RabbitListener(queues = "clientes.queue")
    public void recibirClienteCreado(CuentaDTO cuentaDTO) {
        log.info("Mensaje recibido: Cliente creado con ID " + cuentaDTO.getClienteId());
        cuentaService.createNewDefaultAccount(cuentaDTO);
    }

}
