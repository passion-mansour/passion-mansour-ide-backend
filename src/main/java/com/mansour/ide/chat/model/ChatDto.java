package com.mansour.ide.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {

    private Long chatMessageId;
    private Long userId;
    private Long projectId;
    private String message;
    private LocalDateTime createAt;
    private String sender;
    private MessageType messageType;

    public enum MessageType {
        JOIN, LEAVE, TALK
    }

}
