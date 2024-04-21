package com.mansour.ide.codeEditor.service;

import com.mansour.ide.codeEditor.dto.FilePatchRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.model.File;
import com.mansour.ide.codeEditor.repository.FileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeEditorService {
    @Autowired
    private final FileRepositoryImpl fileRepository;

    @Transactional
    public FileResponse create(){
        File file = File.builder()
                .name("hello, file")
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();

        return FileResponse.from(fileRepository.save(file));
    }

    @Transactional
    public FileResponse save(FilePatchRequest filePatchRequest){
        Optional<File> file = fileRepository.findById(filePatchRequest.getId());
        if(file.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        file.get().setLanguage(filePatchRequest.getLanguage());
        file.get().setContent(filePatchRequest.getContent());
        file.get().setUpdateDateTime(LocalDateTime.now());

        return FileResponse.from(fileRepository.update(file.get()));
    }

    public void run(){
        // TODO: run 구현
    }
}
