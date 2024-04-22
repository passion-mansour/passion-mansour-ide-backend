package com.mansour.ide.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPostRequest {
    @NotNull
    private Boolean isEnd;
}
