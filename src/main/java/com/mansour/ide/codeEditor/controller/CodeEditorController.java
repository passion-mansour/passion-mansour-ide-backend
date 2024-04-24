package com.mansour.ide.codeEditor.controller;

import com.mansour.ide.board.dto.ProjectResponse;
import com.mansour.ide.board.service.ProjectService;
import com.mansour.ide.codeEditor.dto.FilePatchRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.model.CodeResult;
import com.mansour.ide.codeEditor.service.CodeEditorService;
import com.mansour.ide.codeEditor.service.JavaCodeExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class CodeEditorController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CodeEditorService codeEditorService;
    @Autowired
    private JavaCodeExecutor javaCodeExecutor;

    @PostMapping("/{projectId}/create")
    public ResponseEntity<ProjectResponse> saveFile(@PathVariable("projectId") Long projectId){
        log.info("프로젝트 파일 생성 시작");
        projectService.updateFileId(projectId, codeEditorService.create().getId());
        log.info("프로젝트 파일 생성 완료");
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

    @GetMapping("/{projectId}/codeEditor/get")
    public ResponseEntity<FileResponse> getFile(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(codeEditorService.findById(projectService.getById(projectId).getFileId()));
    }

    @PostMapping("/{projectId}/run")
    public ResponseEntity<CodeResult> runFile(@PathVariable("projectId") Long projectId,
                                              @RequestBody FilePatchRequest filePatchRequest){
        CodeResult codeResult;

        // save
        filePatchRequest.setId(projectService.getById(projectId).getFileId());
        FileResponse fileResponse = codeEditorService.save(filePatchRequest);

        // run
        try{
            JavaCodeExecutor.setContent(fileResponse.getContent());
            codeResult = JavaCodeExecutor.run();
        } catch (Exception e){
            throw new IllegalArgumentException("");
        }

        return ResponseEntity.ok(codeResult);
    }
}