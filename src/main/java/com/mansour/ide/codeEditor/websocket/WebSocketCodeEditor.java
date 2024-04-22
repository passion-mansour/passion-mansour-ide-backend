package com.mansour.ide.codeEditor.websocket;

import com.mansour.ide.codeEditor.model.CodeSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSocketCodeEditor {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/change")
    public void handleCodeChange(CodeSnippet codeSnippet) {
        log.info("Received snippet: {}", codeSnippet);

        messagingTemplate.convertAndSend("/topic/codeUpdate/" + codeSnippet.getId(), codeSnippet);
    }
}
