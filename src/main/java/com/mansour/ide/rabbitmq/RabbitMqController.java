package com.mansour.ide.rabbitmq;

import com.mansour.ide.codeEditor.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// 레딧엠큐가 제대로 연결됐는지 확인용
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RabbitMqController {
    @Autowired
    private final RabbitMqService rabbitMqService;

    @PostMapping("/send/message")
    public ResponseEntity<String> sendMessage(@RequestBody FileResponse fileResponse) {
        try {
            log.info(fileResponse.toString());
            log.info("메세지 전송 시작");

            this.rabbitMqService.sendMessage(fileResponse);
            return ResponseEntity.ok("Message sent to RabbitMQ");
        } catch (Exception e) {
            log.error("Error while sending message to RabbitMQ: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message to RabbitMQ");
        }
    }
}
