package com.mansour.ide.board.dto;

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
    private String language; // 언어 태그
    private int maxUser;
    private Boolean isLock;
    private Boolean isEnd;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    private Long fileId;

    // Static Factory Method
    public static ProjectResponse from(Project project){
        return new ProjectResponse(
                project.getId(),
                project.getPw(),
                project.getTitle(),
                project.getTagLanguage(),
                project.getMaxUser(),
                project.getIsLock(),
                project.getIsEnd(),
                project.getCreateDateTime(),
                project.getEndDateTime(),
                project.getFileId()
                );
    }
}
