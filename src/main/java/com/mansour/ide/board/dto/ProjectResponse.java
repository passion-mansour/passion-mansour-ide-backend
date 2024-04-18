package com.mansour.ide.board.dto;

import com.mansour.ide.enums.Language;
import com.mansour.ide.board.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String pw;
    private String title;
    private int maxUser;
    private boolean isLock;
    private boolean isEnd;
    private Language language; // 언어 태그
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    // Static Factory Method
    public static ProjectResponse from(Project board){
        return new ProjectResponse(
                board.getId(),
                board.getPw(),
                board.getTitle(),
                board.getMaxUser(),
                board.isLock(),
                board.isEnd(),
                board.getLanguage(),
                board.getCreateDateTime(),
                board.getUpdateDateTime()
        );
    }
}
