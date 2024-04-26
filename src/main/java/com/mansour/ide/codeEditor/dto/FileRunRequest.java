package com.mansour.ide.codeEditor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileRunRequest {
    private Long id;
    private String language;
    private String content;
}
