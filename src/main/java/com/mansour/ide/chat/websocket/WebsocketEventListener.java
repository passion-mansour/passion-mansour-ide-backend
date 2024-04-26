package com.mansour.ide.chat.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebsocketEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public class UserCounter {
        private ConcurrentHashMap<String, Integer> userCounts = new ConcurrentHashMap<>();

        public void incrementCount(String destination) {
            userCounts.merge(destination, 1, Integer::sum);
        }

        public void decrementCount(String destination) {
            userCounts.merge(destination, -1, (oldValue, value) -> (oldValue + value > 0 ? oldValue + value : 0));
        }

        public int getCount(String destination) {
            return userCounts.getOrDefault(destination, 0);
        }

    }

    private final UserCounter userCounter;

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("headerAccessor {}",headerAccessor);
        String destination = headerAccessor.getDestination();
        userCounter.incrementCount(destination);
        log.info("userCount {}", userCounter.getCount(destination));
        broadcastUserCount(destination);
    }

    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("headerAccessor {}",headerAccessor);
        String destination = headerAccessor.getDestination();
        userCounter.decrementCount(destination);
        log.info("userCount {}", userCounter.getCount(destination));
        broadcastUserCount(destination);
    }

    public void broadcastUserCount(String destination) {
        int count = userCounter.getCount(destination);
        simpMessagingTemplate.convertAndSend("/topic/userCount" + destination, count);
    }


}
