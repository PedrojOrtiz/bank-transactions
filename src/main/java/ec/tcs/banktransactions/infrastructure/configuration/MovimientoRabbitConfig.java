package ec.tcs.banktransactions.infrastructure.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;

@Configuration
public class MovimientoRabbitConfig {

    @Bean
    public Queue movimientosQueue() {
        return QueueBuilder.durable("movimientos.queue")
                .deadLetterExchange("movimientos.dlq.exchange")
                .deadLetterRoutingKey("movimientos.failed")
                .build();
    }

    @Bean
    public Queue movimientosDLQ() {
        return QueueBuilder.durable("movimientos.dlq").build();
    }

    @Bean
    public Exchange movimientosExchange() {
        return ExchangeBuilder.directExchange("movimientos.exchange").build();
    }

    @Bean
    public Binding movimientoBinding() {
        return BindingBuilder.bind(movimientosQueue())
                .to(movimientosExchange())
                .with("movimientos.submitted")
                .noargs();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {  // Inject the JSON converter
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);  // Set JSON converter ✅
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3) // Reintenta 3 veces antes de enviar a DLQ
                .backOffOptions(1000, 2.0, 10000) // Espera 1s, 2s, 4s...
                .build());
        return factory;
    }


}
