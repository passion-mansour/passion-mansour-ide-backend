package com.mansour.ide.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;

    private final ChatService chatService;

    private final Set<WebSocketSession> sessions = new HashSet<>();

    private final Map<Long, Set<WebSocketSession>> projectSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결", session.getId());
        sessions.add(session);
    }

    // 소켓 통신 시 메시지 전송
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("session {}", payload);

        ChatDto chatDto = mapper.readValue(payload, ChatDto.class);
        log.info("session {}", chatDto.toString());

        chatService.saveMessage(chatDto);

        Long projectId = chatDto.getProjectId();
        projectSessionMap.computeIfAbsent(projectId, k -> new HashSet<>()).add(session);

        switch (chatDto.getMessageType()) {
            case JOIN:
                broadcastUserJoined(projectSessionMap.get(projectId), chatDto.getSender());
                break;
            case LEAVE:
                broadcastUserLeft(projectSessionMap.get(projectId), chatDto.getSender());
                projectSessionMap.get(projectId).remove(session);
                break;
            case TALK:
                sendMessageToChatRoom(chatDto, projectSessionMap.get(projectId));
                break;
        }

        // 세션 유지 관리
        if (projectSessionMap.get(projectId).isEmpty()) {
            projectSessionMap.remove(projectId);
        }
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    private void sendMessageToChatRoom(ChatDto chatDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatDto));
    }

    private void broadcastUserJoined(Set<WebSocketSession> chatRoomSessions, String username) {
        ChatDto messageDto = new ChatDto();
        messageDto.setMessage(username + " has joined the chat!");
        messageDto.setMessageType(ChatDto.MessageType.JOIN); // 메시지 유형 설정
        chatRoomSessions.forEach(session -> sendMessage(session, messageDto));
    }

    private void broadcastUserLeft(Set<WebSocketSession> chatRoomSessions, String username) {
        ChatDto messageDto = new ChatDto();
        messageDto.setMessage(username + " has left the chat!");
        messageDto.setMessageType(ChatDto.MessageType.LEAVE); // 메시지 유형 설정
        chatRoomSessions.forEach(session -> sendMessage(session, messageDto));
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            String jsonMessage = mapper.writeValueAsString(message);
            session.sendMessage(new TextMessage(jsonMessage));
        } catch (IOException e) {
            log.error("Error sending message", e);
        }
    }
}
