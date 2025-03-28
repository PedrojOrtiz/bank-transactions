package ec.tcs.banktransactions.infrastructure.event;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.application.service.CuentaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClienteConsumer {

    Logger log = LoggerFactory.getLogger(ClienteConsumer.class);

    @Autowired
    private CuentaService cuentaService;

    @RabbitListener(queues = "clienteQueue")
    public void recibirClienteCreado(CuentaDTO cuentaDTO) {
        log.info("Mensaje recibido: Cliente creado con ID " + cuentaDTO.getClienteId());
        cuentaService.createNewDefaultAccount(cuentaDTO);
    }

}
