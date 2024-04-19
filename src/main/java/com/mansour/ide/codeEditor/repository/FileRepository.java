package com.mansour.ide.codeEditor.repository;

import com.mansour.ide.codeEditor.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    // 파일 저장
    public File save(File file);

    // 파일 업데이트
    public File update(File file);

    // 파일 조회
    public List<File> getAllFile();
    public Optional<File> findById(Long id);
    public Optional<File> getFileByName(String name);
}
