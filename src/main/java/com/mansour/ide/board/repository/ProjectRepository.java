package com.mansour.ide.board.repository;

import com.mansour.ide.board.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    public Project save(Project project);
    Project updateFileId(Project project, Long fileId);

    Project updateEndStatus(Project project, Boolean endStatus);

    public List<Project> getAll();
    public Optional<Project> getById(Long id);
    public Optional<Project> getByTitle(String title);
    public List<Project> getByEndStatus(boolean isEnd);
}