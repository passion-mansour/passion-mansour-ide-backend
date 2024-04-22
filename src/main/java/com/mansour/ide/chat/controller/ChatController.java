package com.mansour.ide.chat.controller;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private ChatService chatService;

    @MessageMapping("/chat/message/{projectId}")
    @SendTo("/topic/chat/{projectId}")
    public ChatDto sendMessage(@DestinationVariable Long projectId, ChatDto chatDto) {
//        return chatService.saveMessage(chatDto);
        log.info("Handling message for project ID: {}", projectId);

        return chatDto;
    }
}