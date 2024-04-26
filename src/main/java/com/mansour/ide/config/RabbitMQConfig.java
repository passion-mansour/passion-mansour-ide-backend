package com.mansour.ide.config;

import com.mansour.ide.codeEditor.model.CodeResult;
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

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RabbitMQConfig {
    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public CodeResult codeResult() {
        // 기본값 또는 설정으로 초기화
        return new CodeResult(false, null, new ArrayList<>());
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("languageExchange");
    }

    @Bean
    public Queue javaQueue() {
        return new Queue("javaQueue", true);
    }

    @Bean
    public Queue pythonQueue() {
        return new Queue("pythonQueue", true);
    }

    @Bean
    public Queue javascriptQueue() {
        return new Queue("javascriptQueue", true);
    }

    @Bean
    public Binding bindingJava(DirectExchange directExchange, Queue javaQueue) {
        return BindingBuilder.bind(javaQueue).to(directExchange).with("java");
    }

    @Bean
    public Binding bindingPython(DirectExchange directExchange, Queue pythonQueue) {
        return BindingBuilder.bind(pythonQueue).to(directExchange).with("python");
    }

    @Bean
    public Binding bindingJavascript(DirectExchange directExchange, Queue pythonQueue) {
        return BindingBuilder.bind(pythonQueue).to(directExchange).with("javascript");
    }

    @Bean // RabbitMQ 연동을 위한 ConnectionFactory 빈을 생성하여 반환
    public CachingConnectionFactory connectionFactory() {
        // Connection 및 Channel을 캐싱하여 재사용
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMqProperties.getHost());
        connectionFactory.setPort(rabbitMqProperties.getPort());
        connectionFactory.setUsername(rabbitMqProperties.getUsername());
        connectionFactory.setPassword(rabbitMqProperties.getPassword());
        return connectionFactory;
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
}
