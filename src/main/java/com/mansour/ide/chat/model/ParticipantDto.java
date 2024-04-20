package com.mansour.ide.chat.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ParticipantDto {
    private Long id;
    private String name;
    private String nickname;
    private String profile;
    private boolean permission;
}
