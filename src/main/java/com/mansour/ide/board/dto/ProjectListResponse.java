package com.mansour.ide.board.dto;

import com.mansour.ide.board.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

<<<<<<< HEAD
@Data
@AllArgsConstructor
public class ProjectListResponse {
    private Long id;
    private String title;

    // Static Factory Method
    public static ProjectListResponse from(Project board){
        return new ProjectListResponse(
                board.getId(),
                board.getTitle()
        );
    }
=======
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectListResponse {
    private List<Project> projects;
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
}
