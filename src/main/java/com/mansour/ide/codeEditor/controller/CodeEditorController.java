package com.mansour.ide.codeEditor.controller;

import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.service.ProjectService;
import com.mansour.ide.codeEditor.dto.FilePatchRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.service.CodeEditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class CodeEditorController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CodeEditorService codeEditorService;

    @PostMapping("/{projectId}/create")
    public ResponseEntity<ProjectResponse> saveFile(@PathVariable("projectId") Long projectId){
        projectService.updateFileId(projectId, codeEditorService.create().getId());
        return ResponseEntity.ok(projectService.getById(projectId));
    }

    @PatchMapping("/{projectId}/save")
    public ResponseEntity<FileResponse> updateFile(@PathVariable("projectId") Long projectId,
                                                   @RequestBody FilePatchRequest filePatchRequest){
        log.info("프로젝트 파일 저장 시작");
        filePatchRequest.setId(projectService.getById(projectId).getFileId());
        log.info("project Id = {}", projectId);
        log.info("file id = {}", filePatchRequest.getId());

        return ResponseEntity.ok(codeEditorService.save(filePatchRequest));
    }
}