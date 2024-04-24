package com.mansour.ide.codeEditor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CodeResult {
    private String content;
    private String stdout;
    private List<String> stderr;
    private boolean exception;
}
