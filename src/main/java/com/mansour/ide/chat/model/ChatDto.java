package com.mansour.ide.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {

    private Long chatMessageId; // 메시지 id
    private Long userId; // 보내는 유저 id
    private Long projectId; // 프로젝트 id
    private String message; // 메시지 내용
    private LocalDateTime createAt; // 보낸 시간
    private String sender; // 보낸 유저 닉네임
    private MessageType messageType; // 메시지 타입

    public enum MessageType {
        JOIN, LEAVE, TALK
    }

}
