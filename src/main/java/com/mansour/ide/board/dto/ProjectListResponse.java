package com.mansour.ide.board.dto;

import com.mansour.ide.board.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectListResponse {
    private List<Project> projects;
}
