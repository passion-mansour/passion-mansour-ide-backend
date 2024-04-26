package com.mansour.ide.codeEditor.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CodeResult {
    private String content;
    private String stdout;
    private List<String> stderr;
    private boolean exception;

    public CodeResult(boolean exception, String stdout, List<String> stderr) {
        this.stdout = stdout;
        this.stderr = stderr;
        this.exception = exception;
    }

    public CodeResult(String content, String stdout, List<String> stderr, boolean exception) {
        this.content = content;
        this.stdout = stdout;
        this.stderr = stderr;
        this.exception = exception;
    }
}