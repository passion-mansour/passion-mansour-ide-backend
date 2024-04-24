package com.mansour.ide.rabbitmq;

import com.mansour.ide.codeEditor.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqService {
    @Value("${rabbitmq.queue.name}")
    private String QUEUE_NAME;

    @Value("${rabbitmq.exchange.name}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.routing.key}")
    private String ROUTING_KEY;

    private final RabbitTemplate rabbitTemplate;

    /**
     * 1. Queue 로 메세지를 발행
     * 2. Producer 역할 -> Direct Exchange 전략
     **/
    public void sendMessage(FileResponse fileResponse) {
        log.info("message send: {}", fileResponse.toString());
        this.rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,fileResponse);
    }

    /**
     * 1. Queue 에서 메세지를 구독
     **/
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(FileResponse fileResponse) {
        log.info("Received Message : {}", fileResponse.toString());
    }
}
