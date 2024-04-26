package com.mansour.ide.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RabbitMqService {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    @Autowired
    public RabbitMqService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public void sendToQueue(String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend("languageExchange", routingKey, message);
            log.info("Message sent to {} queue: {}", message, routingKey);
        } catch (AmqpException e) {
            log.error("Failed to send message to {} queue: {}", e.getMessage(), routingKey);
            throw e;
        }
    }

    public boolean validateMessage(String message) {
        return message != null && !message.trim().isEmpty();
    }
}
