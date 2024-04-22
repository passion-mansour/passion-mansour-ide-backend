package com.mansour.ide.codeEditor.websocket;

import com.mansour.ide.codeEditor.model.CodeSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WebSocketCodeEditor {


    @MessageMapping("code/change/{projectId}")
    @SendTo("/topic/code/{projectId}")
    public CodeSnippet handleCodeChange(@DestinationVariable Long projectId, CodeSnippet codeSnippet) throws Exception{

        log.info("Handling code for project ID: {}", projectId);

        return codeSnippet;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        // 사용자에게 에러 메시지를 보내기
        return "An error occurred: " + exception.getMessage();
    }

}
