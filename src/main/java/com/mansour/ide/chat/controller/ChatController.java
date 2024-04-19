package com.mansour.ide.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ObjectMapper mapper;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅 메시지 처리
    @MessageMapping("/chat")
    public void handleChat(@RequestBody String payload) throws Exception {
        log.info("Received message payload: {}", payload);
        ChatDto chatDto = mapper.readValue(payload, ChatDto.class);
        chatService.saveMessage(chatDto);

        Long projectId = chatDto.getProjectId();
        String destination = "/topic/chat/" + projectId;

        switch (chatDto.getMessageType()) {
            case JOIN:
                broadcastUserJoined(destination, chatDto.getSender());
                break;
            case LEAVE:
                broadcastUserLeft(destination, chatDto.getSender());
                break;
            case TALK:
                sendMessageToChatRoom(destination, chatDto);
                break;
        }
    }

    private void sendMessageToChatRoom(String destination, ChatDto chatDto) {
        messagingTemplate.convertAndSend(destination, chatDto);
    }

    private void broadcastUserJoined(String destination, String username) {
        ChatDto messageDto = new ChatDto();
        messageDto.setMessage(username + " has joined the chat!");
        messageDto.setMessageType(ChatDto.MessageType.JOIN);
        messagingTemplate.convertAndSend(destination, messageDto);
    }

    private void broadcastUserLeft(String destination, String username) {
        ChatDto messageDto = new ChatDto();
        messageDto.setMessage(username + " has left the chat!");
        messageDto.setMessageType(ChatDto.MessageType.LEAVE);
        messagingTemplate.convertAndSend(destination, messageDto);
    }
}

