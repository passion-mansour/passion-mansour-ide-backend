package com.mansour.ide.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {


    private Long userId; // 보내는 유저 id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message; // 메시지 내용
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp; // 보낸 시간
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sender; // 보낸 유저 닉네임
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type; // 메시지 타입

}
