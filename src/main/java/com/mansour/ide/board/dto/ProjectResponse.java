package com.mansour.ide.board.dto;

<<<<<<< HEAD
import com.mansour.ide.enums.Language;
=======
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
import com.mansour.ide.board.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

<<<<<<< HEAD
=======
import java.sql.Timestamp;
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String pw;
    private String title;
<<<<<<< HEAD
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
=======
    private String language; // 언어 태그
    private int maxUser;
    private Boolean isLock;
    private Boolean isEnd;
    private Timestamp createDateTime;
    private Timestamp updateDateTime;

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
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
    }
}
