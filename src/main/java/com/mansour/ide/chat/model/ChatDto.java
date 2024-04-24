package com.mansour.ide.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {

    @JsonIgnore
    private Long chatMessageId; // 메시지 id
    private Long userId; // 보내는 유저 id
    @JsonIgnore
    private Long projectId; // 프로젝트 id
    @JsonIgnore
    private String message; // 메시지 내용
    @JsonIgnore
    private LocalDateTime createAt; // 보낸 시간
    @JsonIgnore
    private String sender; // 보낸 유저 닉네임
    @JsonIgnore
    private String type; // 메시지 타입

}
