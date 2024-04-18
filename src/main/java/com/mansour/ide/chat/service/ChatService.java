package com.mansour.ide.chat.service;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.model.Messages;
import com.mansour.ide.chat.repository.ChatRepository;
import com.mansour.ide.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public ChatDto saveMessage(ChatDto chatDto) {
        Messages messages = new Messages();
        messages.setProjectId(chatDto.getProjectId());
        messages.setParticipantId(chatDto.getUserId());

        Messages saved = chatRepository.save(messages);
        log.info("saved chatMessage {}", saved);

        return convertToDto(saved);
    }

    private ChatDto convertToDto(Messages messages) {
        ChatDto chatDto = new ChatDto();
        // TODO: member findById 메서드 구현 후 유저 이름 가져오기
        chatDto.setChatMessageId(messages.getMessageId());
        chatDto.setMessage(chatDto.getMessage());
        chatDto.setProjectId(messages.getProjectId());
        chatDto.setCreateAt(messages.getCreateAt());

        return chatDto;
    }
}
