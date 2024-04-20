package com.mansour.ide.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    private Long participantId;
    private Long projectId;
    private Long userId;
    private Boolean permission = false;

}
