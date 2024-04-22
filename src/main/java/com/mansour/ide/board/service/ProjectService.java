package com.mansour.ide.board.service;

<<<<<<< HEAD
import com.mansour.ide.board.model.Project;
import com.mansour.ide.board.repository.BoardRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
=======
import com.mansour.ide.board.dto.ProjectListResponse;
import com.mansour.ide.board.dto.ProjectPostRequest;
import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.model.Project;
import com.mansour.ide.board.repository.ProjectRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58

@Service
@RequiredArgsConstructor
public class ProjectService {
<<<<<<< HEAD
    private final BoardRepositoryImpl projectRepository;
    private final List<Project> board = new ArrayList<>();

    // 프로젝트 생성
    // 폴더와 파일 각각 하나씩 생성
    // 호스트 임명


    // 프로젝트 입장


    // 프로젝트 퇴장
=======
    @Autowired
    private final ProjectRepositoryImpl projectRepository;

    // 프로젝트 생성
    @Transactional
    public ProjectResponse create(ProjectPostRequest projectPostRequest){
        Project project = Project.builder()
                .hostId(projectPostRequest.getHostId())
                .pw(projectPostRequest.getPw())
                .title(projectPostRequest.getTitle())
                .tagLanguage(projectPostRequest.getTagLanguage())
                .maxUser(projectPostRequest.getMaxUser())
                .isLock(projectPostRequest.getIsLock())
                .isEnd(false)
                .createDateTime(Timestamp.from(Instant.now()))
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
    @Transactional
    public ProjectResponse updateFileId(Long id, Long fileId){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        return ProjectResponse.from(projectRepository.updateFileId(project.get(), fileId));
    }

    @Transactional
    public ProjectResponse updateEndStatus(Long id, boolean endStatus){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        return ProjectResponse.from(projectRepository.updateEndStatus(project.get(), endStatus));
    }
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
}
