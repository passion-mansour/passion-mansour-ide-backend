package com.mansour.ide.codeEditor.dto;

import com.mansour.ide.codeEditor.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileResponse {
    private Long id;
    private String name;
    private String content;
    private String language;
    private Timestamp createDateTime;
    private Timestamp updateDateTime;

    public static FileResponse from(File file){
        return new FileResponse(
                file.getId(),
                file.getName(),
                file.getContent(),
                file.getLanguage(),
                file.getCreateDateTime(),
                file.getUpdateDateTime()
        );
    }
}
