package com.mansour.ide.codeEditor.controller;

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
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @PostMapping("/{projectId}/create")
    public
    RedirectView saveFile(@PathVariable("projectId") Long projectId, RedirectAttributes redirectAttributes){
        projectService.updateFileId(projectId, codeEditorService.create().getId());

        String redirectApi = "redirect:/api/projects/" + projectId + "/get";
        log.info("redirect api : {}", redirectApi);

        redirectAttributes.addFlashAttribute("success message", "프로젝트가 성공적으로 생성되었습니다.");
        return new RedirectView(redirectApi);
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