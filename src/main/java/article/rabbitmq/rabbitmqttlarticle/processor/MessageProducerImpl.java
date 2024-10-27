package article.rabbitmq.rabbitmqttlarticle.processor;

import article.rabbitmq.rabbitmqttlarticle.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerImpl implements MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void sendMessage(Object message, String expiration) {
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.QUEUE_TTL_NAME,
                new GenericMessage<Object>(message),
                //Se necessàrio adicionar headers apenas
                msg -> {
                    //Tempo de expiração da mensagem personalizado
            msg.getMessageProperties().setExpiration(expiration);

            msg.getMessageProperties().setHeader("ORIGIN", "sendMessage(Object message)");
            msg.getMessageProperties().setContentType("application/json");
            return msg;
        });
    }
}
