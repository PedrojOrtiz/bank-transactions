package ec.tcs.banktransactions.infrastructure.event;

import ec.tcs.banktransactions.application.dto.MovimientoDTO;
import ec.tcs.banktransactions.application.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MovimientoConsumer {

    private final Logger log = LoggerFactory.getLogger(MovimientoConsumer.class);

    private final MovimientoService movimientoService;
    private final RedisTemplate<Long, Object> redisTemplate;

    @RabbitListener(queues = "movimientos.queue")
    public void procesarMovimiento(MovimientoDTO movimientoDTO) {

        log.info("📥 Mensaje recibido: {}", movimientoDTO);

        if (esMovimientoProcesado(movimientoDTO.getId())) {
            log.info("⚠️ Movimiento ya procesado: {}", movimientoDTO.getId());
            return;
        }

        try {
            log.info("📥 Procesando movimiento: {}", movimientoDTO);
            /// Save transaction & store in Redis
            marcarMovimientoProcesado(
                    movimientoService.createMovimiento(movimientoDTO).getId()
            );
        } catch (Exception e) {
            log.error("❌ Error processing transaction: {}", movimientoDTO, e);
            throw new AmqpRejectAndDontRequeueException("Transaction failed");
        }
    }


    public boolean esMovimientoProcesado(Long idMovimiento) {
        return idMovimiento != null && redisTemplate.hasKey(idMovimiento);
    }

    public void marcarMovimientoProcesado(Long idMovimiento) {
        redisTemplate.opsForValue().set(idMovimiento, "PROCESADO", Duration.ofMinutes(10));
    }

}
