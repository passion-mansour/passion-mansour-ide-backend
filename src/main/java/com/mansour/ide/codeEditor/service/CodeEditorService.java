package com.mansour.ide.codeEditor.service;

import com.mansour.ide.codeEditor.dto.FilePatchRequest;
import com.mansour.ide.codeEditor.dto.FileResponse;
import com.mansour.ide.codeEditor.model.File;
import com.mansour.ide.codeEditor.repository.FileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeEditorService {
    @Autowired
    private final FileRepositoryImpl fileRepository;

    @Transactional
    public FileResponse create(){
        File file = File.builder()
                .name("hello, file")
                .createDateTime(Timestamp.from(Instant.now()))
                .build();

        return FileResponse.from(fileRepository.save(file));
    }

    @Transactional
    public FileResponse save(FilePatchRequest filePatchRequest){
        Optional<File> file = fileRepository.findById(filePatchRequest.getId());
        log.info("파일 찾기 성공");
        log.info("{}", file.get().getId());
        log.info(file.get().getLanguage());
        log.info(file.get().getContent());
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File info is not found!");
        }

        file.get().setLanguage(filePatchRequest.getLanguage());
        file.get().setContent(filePatchRequest.getFileContent());
        file.get().setUpdateDateTime(Timestamp.from(Instant.now()));

        log.info("파일 업데이트 시작");
        return FileResponse.from(fileRepository.update(file.get()));
    }

    public FileResponse findById(Long id){
        Optional<File> file = fileRepository.findById(id);
        if(file.isEmpty()){
            throw new IllegalArgumentException("File info is not found!");
        }

        return FileResponse.from(file.get());
    }

    public void run(){
        // TODO: run 구현
    }
}
