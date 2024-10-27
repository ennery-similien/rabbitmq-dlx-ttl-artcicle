package article.rabbitmq.rabbitmqttlarticle.processor;

import article.rabbitmq.rabbitmqttlarticle.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MessageConsumer {

    Logger logger = Logger.getGlobal();

    @RabbitListener(queues = RabbitmqConfig.QUEUE_PROCESSOR)
    public void receiveMessage(GenericMessage<Object> message) {
        logger.log(Level.INFO, "RECEBIDO CORPO>> " + message.getPayload().toString());
        logger.log(Level.INFO, "RECEBIDOS HEADERS >> " + message.getHeaders().toString());
        logger.log(Level.INFO, "");
    }
}
