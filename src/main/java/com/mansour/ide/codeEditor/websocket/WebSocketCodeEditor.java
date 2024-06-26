package com.mansour.ide.codeEditor.websocket;

import com.mansour.ide.board.service.ProjectService;
import com.mansour.ide.chat.websocket.WebsocketEventListener;
import com.mansour.ide.board.dto.ProjectDto;
import com.mansour.ide.codeEditor.model.CodeSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WebSocketCodeEditor {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ProjectService projectService;

    @MessageMapping("/code/change/{projectId}")
//    @SendTo("/topic/code/{projectId}")
    public void handleCodeChange(@DestinationVariable Long projectId, CodeSnippet codeSnippet) throws Exception {

        log.info("Handling code for project ID: {}, fileContent: {}", projectId, codeSnippet.getFileContent());

        String destination = "/topic/code/" + projectId;

        log.info("send {}", codeSnippet);
        simpMessagingTemplate.convertAndSend(destination, codeSnippet);
    }

    @MessageMapping("/project/join/{projectId}")
    @SendTo("/topic/code/{projectId}")
    public ProjectDto handleUserJoin(@DestinationVariable Long projectId, @Payload ProjectDto projectDto) {

        String destination = "/topic/code/" + projectId;

        return projectDto;
    }

    @MessageMapping("/project/leave/{projectId}")
    @SendTo("/topic/code/{projectId}")
    public ProjectDto handleUserExit(@DestinationVariable Long projectId, @Payload ProjectDto projectDto) {

        String destination = "/topic/code/" + projectId;

        if (projectDto.getIsOwn().equals(true)) {
            projectDto.setMessage("호스트가 연결을 종료했습니다,");
            projectService.updateEndStatus(projectId, true);
        }
        projectDto.setType("FORCE_DISCONNECT");
        projectDto.setIsEnd(true);

        return projectDto;
    }
}
