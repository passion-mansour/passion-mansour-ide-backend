package com.mansour.ide.codeEditor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilePostRequest {
    @NotNull
    private Long id;
    @NotNull
    private String language;
    private String content;
}
