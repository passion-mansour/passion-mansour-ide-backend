package com.mansour.ide.chat.websocket;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
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
