package com.mansour.ide.board.dto;

import com.mansour.ide.board.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

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
}
