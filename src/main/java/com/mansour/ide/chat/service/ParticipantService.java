package com.mansour.ide.chat.service;

import com.mansour.ide.chat.model.Participant;
import com.mansour.ide.chat.repository.ParticipantRepository;
import com.mansour.ide.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipantService {

    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;

    public void addParticipant(Long projectId, Long userId) {
        Participant participant = new Participant();
        participant.setProjectId(projectId);
        participant.setUserId(userId);
        participantRepository.save(participant);
    }
}
