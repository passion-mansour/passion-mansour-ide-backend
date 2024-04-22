package com.mansour.ide.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectPostRequest {
    @NotNull
    private Long hostId;
    private String pw;
    @NotNull
    private String title;
    @NotNull
    private String tagLanguage; // 태그용 언어
    @NotNull
    private int maxUser;
    @NotNull
    private Boolean isLock;
}