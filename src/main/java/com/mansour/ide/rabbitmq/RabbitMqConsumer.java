package com.mansour.ide.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConsumer {

    @Autowired
    private SimpMessagingTemplate template;

//    @RabbitListener(queues = "java.queue")
//    public void receiveMessage(FileResponse fileResponse) {
//        System.out.println("Received code for execution: " + fileResponse.getContent());
//
//        // 여기에 코드 실행 및 결과 처리 로직 추가 (쿠베라네티스워커?)
//
//    }


//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = 큐이름, durable = "true"),
//            exchange = @Exchange(value = 익스체인지이름, type = "fanout"),
//            key = 라우팅키
//    ))
//    public void method1(SagaEventMessage message, Channel channel) throws IOException {
//
//    }
}
