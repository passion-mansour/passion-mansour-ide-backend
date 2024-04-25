package com.mansour.ide.chat.controller;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.service.ChatService;
import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/{projectId}")
    @SendTo("/topic/chat/{projectId}")
    public ChatDto sendMessage(@DestinationVariable Long projectId, ChatDto chatDto) {

//        ChatDto savedMessage = chatService.saveMessage(projectId, chatDto);
//        log.info("savedMessage {}", savedMessage);

        Member member = memberRepository.findById(chatDto.getUserId());
        chatDto.setSender(member.getNickName());

        return chatDto;
    }

    @MessageMapping("/chat/join/{projectId}")
    @SendTo("/topic/chat/{projectId}")
    public ChatDto handleChatJoin(@DestinationVariable Long projectId, ChatDto chatDto) {

        Member member = memberRepository.findById(chatDto.getUserId());

        chatDto.setSender(member.getNickName());
        chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");
        return chatDto;
    }

    @MessageMapping("/chat/leave/{projectId}")
    @SendTo("/topic/chat/{projectId}")
    public ChatDto handleChatLeave(@DestinationVariable Long projectId, ChatDto chatDto) {

        Member member = memberRepository.findById(chatDto.getUserId());

        chatDto.setSender(member.getNickName());
        chatDto.setMessage(chatDto.getSender() + "님이 퇴장하셨습니다.");
        return chatDto;
    }
}