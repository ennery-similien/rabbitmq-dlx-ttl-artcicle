package article.rabbitmq.rabbitmqttlarticle.processor;

public interface MessageProducer {
    void sendMessage(Object message, String expiration);
}
