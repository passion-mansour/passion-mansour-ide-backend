package com.mansour.ide.codeEditor.dto;

import com.mansour.ide.codeEditor.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FilePostRequest {
    private Long id;
    private String name;
    private String content;
    private String language;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public static FilePostRequest from(File file){
        return new FilePostRequest(
                file.getId(),
                file.getName(),
                file.getContent(),
                file.getLanguage(),
                file.getCreateDateTime(),
                file.getUpdateDateTime()
        );
    }
}
