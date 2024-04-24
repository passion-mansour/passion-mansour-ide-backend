package com.mansour.ide.board.service;

import com.mansour.ide.board.dto.ProjectListResponse;
import com.mansour.ide.board.dto.ProjectPostRequest;
import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.model.Project;
import com.mansour.ide.board.repository.ProjectRepositoryImpl;
import com.mansour.ide.member.dto.MemberDTO;
import com.mansour.ide.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepositoryImpl projectRepository;
    @Autowired
    private final MemberService memberService;

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

        MemberDTO host = memberService.findMemberDetailsById(projectPostRequest.getHostId());
        return ProjectResponse.from(projectRepository.save(project), host);
    }

    // 프로젝트 조회
    public ProjectResponse getById(Long id){
        Optional<Project> project = projectRepository.getById(id);

        if(project.isEmpty()){
            throw new IllegalArgumentException("Project info is not found!");
        }
        MemberDTO host = memberService.findMemberDetailsById(project.get().getHostId());
        return ProjectResponse.from(project.get(), host);
    }

    // 종료 여부에 따른 프로젝트 List 조회
    public ProjectListResponse getByEndStatus(boolean isEnd){
        return new ProjectListResponse(projectRepository.getByEndStatus(isEnd));
    }

    // update
    @Transactional
    public void updateFileId(Long id, Long fileId){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        MemberDTO host = memberService.findMemberDetailsById(project.get().getHostId());
        ProjectResponse.from(projectRepository.updateFileId(project.get(), fileId), host);
    }

    @Transactional
    public ProjectResponse updateEndStatus(Long id, boolean endStatus){
        Optional<Project> project = projectRepository.getById(id);
        if(project.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        MemberDTO host = memberService.findMemberDetailsById(project.get().getHostId());
        return ProjectResponse.from(projectRepository.updateEndStatus(project.get(), endStatus), host);
    }
}
