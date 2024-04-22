package com.mansour.ide.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Messages {

    private Long projectId;
    private Long MessageId;
    private Long participantId;
    private String message;
    private LocalDateTime createAt;


}
