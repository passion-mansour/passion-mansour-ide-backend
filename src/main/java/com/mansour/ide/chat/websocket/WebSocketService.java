package com.mansour.ide.chat.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    // 구독 사용자 수 반환
    // 웹소켓 사용자 세션 정보
    @Autowired
    private SimpUserRegistry userRegistry;
    public int getSubscriberCount(String destination) {
        return userRegistry.getUsers().stream()
            .flatMap(user -> user.getSessions().stream())
            .flatMap(session -> session.getSubscriptions().stream())
            .filter(subscription -> subscription.getDestination()
                .equals(destination)) .collect(Collectors.toList()).size(); }
}
