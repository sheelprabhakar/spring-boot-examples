package com.code4copy.rabbitmqexample.core.service.sender;

import com.code4copy.rabbitmqexample.core.data.domain.MessageLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MessageSender {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    @Value("${c4c.topic.exchange}")
    private String exchange;

    @Value("${c4c.topic.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private Integer id=0;
    @Autowired
    public MessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 1000L)
    public void sendMessage1() {
        final MessageLoad message = new MessageLoad(String.valueOf(++id),
                "Message at time " + LocalTime.now().toString());
        LOG.info("Sending message...");
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Scheduled(fixedDelay = 1000L)
    public void sendMessage2() {
        final MessageLoad message = new MessageLoad(String.valueOf(++id),
                "Message at time " + LocalTime.now().toString());
        LOG.info("Sending message...");
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
