package com.mansour.ide.board.controller;

import com.mansour.ide.board.dto.BoardPostRequest;
import com.mansour.ide.board.dto.ProjectListResponse;
import com.mansour.ide.board.dto.ProjectPostRequest;
import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // 프로젝트 생성시 파일 생성 api로 포워딩, 이후 다시 get 프로젝트로 리다리렉션
    @PostMapping("/projects")
    public ModelAndView createProject(@RequestBody ProjectPostRequest projectPostRequest, HttpServletRequest request){
        ModelAndView forwardApi = new ModelAndView("forward:/api/projects/" + projectService.create(projectPostRequest).getId() + "/create");
        forwardApi.addAllObjects(request.getParameterMap());
        return forwardApi;
    }


    // 프로젝트 조회
    @GetMapping("/projects/{projectId}/get")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(projectService.getById(projectId));
    }

    // 종료상태 별 모든 프로젝트 조회
    @PostMapping("/board")
    public ResponseEntity<ProjectListResponse> getProjectsByEndState(@RequestBody BoardPostRequest boardPostRequest){
        log.info("isEnd status = {}", boardPostRequest.getIsEnd());
        return ResponseEntity.ok(projectService.getByEndStatus(boardPostRequest.getIsEnd()));
    }

    // 프로젝트 퇴장
    @GetMapping("/projects/{projectId}/leave")
    public ResponseEntity<ProjectResponse> leaveProject(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(projectService.updateEndStatus(projectId, true));
    }
}
