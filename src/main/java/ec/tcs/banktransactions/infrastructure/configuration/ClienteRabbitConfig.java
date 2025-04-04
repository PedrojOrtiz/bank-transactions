package ec.tcs.banktransactions.infrastructure.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteRabbitConfig {

    @Bean
    public Queue clienteQueue() {
        return new Queue("clientes.queue", true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("clientes.exchange");
    }

    @Bean
    public Binding clienteBinding(Queue clienteQueue, DirectExchange exchange) {
        return BindingBuilder.bind(clienteQueue).to(exchange).with("clientes.created");
    }

}
