package article.rabbitmq.rabbitmqttlarticle;

import article.rabbitmq.rabbitmqttlarticle.processor.MessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class RabbitmqTtlArticleApplication implements CommandLineRunner {

    Logger logger = Logger.getGlobal();
    private final MessageProducer producer;

    public RabbitmqTtlArticleApplication(MessageProducer producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTtlArticleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("");

        for (int i = 1; i <= 3; i++) {

            String message = String.format("Mensagem fila TTL #%s", i);

            String expiration = String.format("%s", i * 60000);

            producer.sendMessage(message, expiration);

            logger.info("ENVIADO >>> " + message + " com TTL de " + i + " minuto(s)");
            logger.info("");

            Thread.sleep(10000);
        }
    }
}
