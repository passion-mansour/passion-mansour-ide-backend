package com.mansour.ide.chat.service;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.model.Message;
import com.mansour.ide.chat.repository.ChatRepository;
import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatDto saveMessage(Long projectId, ChatDto chatDto) {

        Message message = new Message();
        message.setProjectId(projectId);
        message.setMessage(chatDto.getMessage());
        message.setCreatedAt(chatDto.getCreateAt());

        Message saved = chatRepository.save(message);

        return convertToDto(saved);
    }

    private ChatDto convertToDto(Message message) {

        Member findUser = memberRepository.findById(message.getUserId());

        ChatDto chatDto = new ChatDto();
        chatDto.setSender(findUser.getNickName());
        chatDto.setChatMessageId(message.getMessageId());
        chatDto.setMessage(chatDto.getMessage());
        chatDto.setProjectId(message.getProjectId());
        chatDto.setCreateAt(message.getCreatedAt());

        return chatDto;
    }

}
