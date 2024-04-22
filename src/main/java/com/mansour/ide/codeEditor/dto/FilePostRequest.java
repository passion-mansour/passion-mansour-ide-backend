package com.mansour.ide.codeEditor.dto;

import com.mansour.ide.codeEditor.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FilePostRequest {
    private Long id;
    private String name;
    private String content;
    private String language;
    private Timestamp createDateTime;
    private Timestamp updateDateTime;

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
