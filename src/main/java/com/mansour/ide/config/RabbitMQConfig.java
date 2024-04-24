package com.mansour.ide.config;

import com.mansour.ide.rabbitmq.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RabbitMQConfig {
    private final RabbitMqProperties rabbitMqProperties;

    @Value("${rabbitmq.queue.name}")
    private String QUEUE_NAME;

    @Value("${rabbitmq.exchange.name}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.routing.key}")
    private String ROUTING_KEY;

    @Bean
    public Queue queue() {
        log.info("QUEUE_NAME: {}", QUEUE_NAME);
        log.info("EXCHANGE_NAME: {}", EXCHANGE_NAME);
        log.info("ROUTING_KEY: {}", ROUTING_KEY);
        return new Queue(QUEUE_NAME);
    }

    @Bean // 지정된 Exchange 이름으로 Direct Exchange Bean 을 생성
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean // Queue 와 Exchange 을 Binding 하고, Routing Key 을 이용하여 Binding Bean 생성
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean // ConnectionFactory 로 연결 후 실제 작업을 위한 Template
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean // 직렬화(메세지를 JSON 으로 변환하는 Message Converter)
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean // RabbitMQ 연동을 위한 ConnectionFactory 빈을 생성하여 반환
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMqProperties.getHost());
        connectionFactory.setPort(rabbitMqProperties.getPort());
        connectionFactory.setUsername(rabbitMqProperties.getUsername());
        connectionFactory.setPassword(rabbitMqProperties.getPassword());
        return connectionFactory;
    }
}
