package com.mansour.ide.codeEditor.controller;

import com.mansour.ide.board.service.ProjectService;
import com.mansour.ide.codeEditor.dto.FilePatchRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.service.CodeEditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class CodeEditorController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CodeEditorService codeEditorService;

    @PostMapping("/{projectId}/create")
    public ModelAndView saveFile(@PathVariable("projectId") Long projectId, RedirectAttributes redirectAttributes){
        projectService.updateFileId(projectId, codeEditorService.create().getId());

        ModelAndView redirectApi = new ModelAndView();
        redirectApi.setViewName("redirect:/api/projects/" + projectId + "/get");
        return redirectApi;
    }

    @PatchMapping("/{projectId}/save")
    public ResponseEntity<FileResponse> updateFile(@PathVariable("projectId") Long projectId,
                                                   @RequestBody FilePatchRequest filePatchRequest){
        filePatchRequest.setId(projectService.getById(projectId).getFileId());
        return ResponseEntity.ok(codeEditorService.save(filePatchRequest));
    }

}