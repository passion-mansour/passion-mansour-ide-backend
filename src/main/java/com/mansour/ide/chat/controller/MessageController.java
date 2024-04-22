package com.mansour.ide.chat.controller;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final ChatService chatService;

    @PostMapping("/save/message")
    public ResponseEntity<ChatDto> save(@RequestBody ChatDto chatDto) {
        ChatDto savedMessage = chatService.saveMessage(chatDto);
        return ResponseEntity.ok(savedMessage);
    }

}
