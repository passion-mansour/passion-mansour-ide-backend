package com.mansour.ide.chat.service;

import com.mansour.ide.chat.model.ChatDto;
import com.mansour.ide.chat.model.Message;
import com.mansour.ide.chat.model.Participant;
import com.mansour.ide.chat.repository.ChatRepository;
import com.mansour.ide.chat.repository.ParticipantRepository;
import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public ChatDto saveMessage(ChatDto chatDto) {

        Message message = new Message();
        message.setProjectId(chatDto.getProjectId());
        message.setMessage(chatDto.getMessage());

        Message saved = chatRepository.save(message);

        return convertToDto(saved);
    }

    private ChatDto convertToDto(Message message) {

        Long userId = participantRepository.findUserIdById(message.getParticipantId());
        Member findUser = memberRepository.findById(userId);

        ChatDto chatDto = new ChatDto();
        chatDto.setSender(findUser.getNickName());
        chatDto.setChatMessageId(message.getMessageId());
        chatDto.setMessage(chatDto.getMessage());
        chatDto.setProjectId(message.getProjectId());
        chatDto.setCreateAt(message.getCreatedAt());

        return chatDto;
    }

}
