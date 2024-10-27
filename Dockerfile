
FROM rabbitmq:4.0.2-management

ENV RABBITMQ_HOME=/opt/rabbitmq

RUN mkdir -p $RABBITMQ_HOME/plugins

ADD https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v4.0.2/rabbitmq_delayed_message_exchange-4.0.2.ez $RABBITMQ_HOME/plugins/rabbitmq_delayed_message_exchange-4.0.2.ez

RUN chown rabbitmq:rabbitmq $RABBITMQ_HOME/plugins/rabbitmq_delayed_message_exchange-4.0.2.ez

RUN rabbitmq-plugins enable rabbitmq_delayed_message_exchange rabbitmq_stomp rabbitmq_web_stomp

EXPOSE 5672 15672 61613 15674

CMD ["rabbitmq-server"]
