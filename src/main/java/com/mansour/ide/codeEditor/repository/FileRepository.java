package com.mansour.ide.codeEditor.repository;

import com.mansour.ide.codeEditor.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    public File save(File file);

    public File update(File file);

    public List<File> getAllFile();
    public Optional<File> findById(Long id);
    public Optional<File> getFileByName(String name);
}
