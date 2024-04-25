package com.mansour.ide.codeEditor.websocket;

import com.mansour.ide.codeEditor.model.CodeSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WebSocketCodeEditor {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/code/change/{projectId}")
    public void handleCodeChange(@DestinationVariable Long projectId, CodeSnippet codeSnippet) throws Exception{

        log.info("Handling code for project ID: {}, fileContent: {}", projectId, codeSnippet.getFileContent());

        String topic = "/topic/code/" + projectId;
        simpMessagingTemplate.convertAndSend(topic, codeSnippet);

        log.info("send {}", codeSnippet);

    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        // 사용자에게 에러 메시지를 보내기
        return "An error occurred: " + exception.getMessage();
    }

}
