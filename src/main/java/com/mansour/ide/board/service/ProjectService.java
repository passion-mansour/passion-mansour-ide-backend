package com.mansour.ide.board.service;

import com.mansour.ide.board.dto.ProjectListResponse;
import com.mansour.ide.board.dto.ProjectPostRequest;
import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.model.Project;
import com.mansour.ide.board.repository.ProjectRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepositoryImpl projectRepository;

    // 프로젝트 생성
    public ProjectResponse create(ProjectPostRequest projectPostRequest){
        Project project = Project.builder()
                .hostId(projectPostRequest.getHostId())
                .pw(projectPostRequest.getPw())
                .title(projectPostRequest.getTitle())
                .tagLanguage(projectPostRequest.getTagLanguage())
                .maxUser(projectPostRequest.getMaxUser())
                .isLock(projectPostRequest.getIsLock())
                .isEnd(false)
                .createDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.MIN)
                .build();

        return ProjectResponse.from(projectRepository.save(project));
    }

    // 프로젝트 조회
    public ProjectResponse getById(Long id){
        Optional<Project> project = projectRepository.getById(id);

        if(project.isEmpty()){
            throw new IllegalArgumentException("Project info is not found!");
        }
        return ProjectResponse.from(project.get());
    }

    // 종료 여부에 따른 프로젝트 List 조회
    public ProjectListResponse getByEndStatus(boolean isEnd){
        return new ProjectListResponse(projectRepository.getByEndStatus(isEnd));
    }

    // update
    public ProjectResponse updateFileId(Long id, Long fileId){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        return ProjectResponse.from(projectRepository.updateFileId(project.get(), fileId));
    }

    public ProjectResponse updateEndStatus(Long id, boolean endStatus){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        return ProjectResponse.from(projectRepository.updateEndStatus(project.get(), endStatus));
    }
}
