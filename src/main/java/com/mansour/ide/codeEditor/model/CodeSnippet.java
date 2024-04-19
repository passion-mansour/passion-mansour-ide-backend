package com.mansour.ide.codeEditor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeSnippet {
    private Long id;
    private String content;
}