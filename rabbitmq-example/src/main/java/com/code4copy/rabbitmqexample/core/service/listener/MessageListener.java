package com.code4copy.rabbitmqexample.core.service.listener;

import com.code4copy.rabbitmqexample.core.data.domain.MessageLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = "${c4c.queue1}")
    public void receiveMessage1(final MessageLoad messageLoad) {
        LOG.info("Received message as a AMQP 'MessageLoad' wrapper: {}", messageLoad.toString());
    }

    @RabbitListener(queues = "${c4c.queue2}")
    public void receiveMessage2(final MessageLoad messageLoad) {
        LOG.info("Received message and deserialized to 'MessageLoad': {}", messageLoad.toString());
    }

    // Can be invoked via MessageListener adapter config
    // If don't want to use @RabbitListener annotation
    public void receiveMessage(final MessageLoad message) {
        LOG.info("Received message and deserialized to 'MessageLoad': {}", message.toString());
    }
}
