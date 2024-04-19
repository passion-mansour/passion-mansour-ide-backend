package com.mansour.ide.codeEditor.dto;

import com.mansour.ide.codeEditor.model.File;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FileResponse {
    private Long id;
    private String name;
    private String content;
    private String language;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public FileResponse(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.content = file.getContent();
        this.language = file.getLanguage();
        this.createDateTime = file.getCreateDateTime();
        this.updateDateTime = file.getUpdateDateTime();
    }
}
