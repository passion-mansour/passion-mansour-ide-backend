package com.mansour.ide.rabbitmq;

import com.mansour.ide.codeEditor.dto.FileResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    // << 프로젝트용 >>
//    public void sendMessage(FileResponse fileResponse) {
//        // RabbitMQ 서버에 연결된 RabbitTemplate을 사용하여 메시지 전송
//        try {
//            // 메시지 생성
//            String language = fileResponse.getLanguage();
//            String code = fileResponse.getContent();
//
//            // 메시지 전송
//            rabbitTemplate.convertAndSend("${rabbitmq.queue.name}" + language, code);
//
//            // 전송 확인
//            System.out.println("Message sent successfully for language " + language);
//            } catch (Exception e) {
//            // 오류 처리
//            System.err.println("Failed to send message: " + e.getMessage());
//        }
//    }
}
