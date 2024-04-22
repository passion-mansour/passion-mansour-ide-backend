package com.mansour.ide.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private Long MessageId;
    private Long projectId;
    private Long participantId;
    private String message;
    private LocalDateTime createdAt;

}
