package com.code4copy.rabbitmqexample.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Value("${c4c.queue1}")
    private String queue1;

    @Value("${c4c.queue2}")
    private String queue2;

    @Value("${c4c.topic.exchange}")
    private String exchange;

    @Value("${c4c.topic.routing.key}")
    private String routingKey;

    @Bean(name = "queue1")
    Queue queue1(){
        // Durable false, not survive server restart
        // Exclusive connection true
        // Auto delete true
        return new Queue(queue1, false, true, true);
    }

    @Bean(name = "queue2")
    Queue queue2(){
        // Durable false, not survive server restart
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-single-active-consumer", true);
        return new Queue(queue2, false, false, false, arguments);
    }

    // Create exchange base on topic
    @Bean(name = "topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding1( @Qualifier("queue1") Queue queue,
                            @Qualifier("topicExchange") TopicExchange exchange){
        // Routing ke can be queue name exact or with patterns
        // Can use multiple using coma separated
        // * (star) can substitute for exactly one word.
        // # (hash) can substitute for zero or more words.
        // Example *.CORE.#,#.LIFE_CYCLE.#,#.SUBSCRIPTION_TERMS.#,#.SNAPSHOT.*
        // https://www.rabbitmq.com/tutorials/tutorial-five-python.html
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    @Bean
    public Binding binding2( @Qualifier("queue2") Queue queue,
                             @Qualifier("topicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // Below configs are optional, remove the two methods below to use
    // the default serialization / deserialization (instead of JSON)
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    // Below two method are used to configure listener using code with default serializer
    // This can be done using @RabbitListener annotation also
    /*@Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue1);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    MessageListenerAdapter listenerAdapter(MessageListener receiver) {
        // bean and listener method name
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/
}
