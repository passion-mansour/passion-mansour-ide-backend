package com.mansour.ide.codeEditor.controller;

import com.mansour.ide.codeEditor.dto.FilePostRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.service.CodeEditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/projects")
public class CodeEditorController {
    @Autowired
    private CodeEditorService codeEditorService;

    @PostMapping("/{projectId}/create")
    public ResponseEntity<FileResponse> saveFile(@PathVariable("projectId") Long projectId,
                                                 @RequestBody FilePostRequest filePostRequest){
        // TODO: project 찾기
        log.info("projectId={}", projectId);
        log.info("language={}", filePostRequest.getLanguage());
        log.info("content={}", filePostRequest.getContent());

        return ResponseEntity.ok(codeEditorService.create(filePostRequest));
    }

    @PatchMapping("/{projectId}/save")
    public ResponseEntity<FileResponse> updateFile(@PathVariable("projectId") Long projectId,
                                                   @RequestBody FilePostRequest filePostRequest){
        // TODO: project 찾기
        log.info("projectId={}", projectId);
        log.info("language={}", filePostRequest.getLanguage());
        log.info("content={}", filePostRequest.getContent());

        return ResponseEntity.ok(codeEditorService.save(filePostRequest));
    }
}