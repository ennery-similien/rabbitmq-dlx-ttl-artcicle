package article.rabbitmq.rabbitmqttlarticle.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.GenericMessage;

import java.nio.charset.StandardCharsets;

@Configuration
public class RabbitmqConfig {
    //Nome do Dead Letter Exchange
    public static final String EXCHANGE_NAME = "dead_letter_exchange";
    //Nome da fila que recebe as mensagens com TTL
    public static final String QUEUE_TTL_NAME = "queue_ttl";
    //Nome do Dead Letter Routing Key. A chave que indica para qual fila as mensagens serão encaminhadas
    //depois de expirar o TTL na fila de retenção (fila com TTL)
    public static final String ROUTING_KEY = "dead_letter_routing_K";
    //Nome da fila que recebe as mensagens que expiraram o TTL,
    //para qual o exchange encaminharà as mensagem para que sejam processadas
    // pelo listener
    public static final String QUEUE_PROCESSOR = "queue_processor";



    //o Dead Letter Exchange
    @Bean
    public DirectExchange exchangeTTL() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    //A fila que recebe as mensagens com TTL
    @Bean
    public Queue queueTTL() {
        return QueueBuilder.durable(QUEUE_TTL_NAME)
                .deadLetterExchange(EXCHANGE_NAME)
                .deadLetterRoutingKey(ROUTING_KEY)
                //.ttl(60000) //TTL de 60 segundos fixo em milisegundos
                .build();
    }

    //A fila que recebe as mensagens que expiraram o TTL,
    @Bean
    public Queue queueTTLProcessor() {
        return new Queue(QUEUE_PROCESSOR, Boolean.TRUE);
    }

    //O binding que liga a fila de processamento com o exchange e a chave de roteamento
    @Bean
    public Binding bindingTTLProcessor() {
        return BindingBuilder.bind(queueTTLProcessor())
                .to(exchangeTTL()).with(ROUTING_KEY);
    }



    //Criando um converter de mensagens, (envio e recebimento)
    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object message, MessageProperties messageProperties)
                    throws MessageConversionException {
                return new Message(
                        message.toString().getBytes(StandardCharsets.UTF_8),
                        messageProperties
                );
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                return new GenericMessage<Object>(
                        new String(message.getBody(), StandardCharsets.UTF_8),
                        message.getMessageProperties().getHeaders()
                );
            }
        };
    }

}
