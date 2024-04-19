package com.mansour.ide.codeEditor.service;

import com.mansour.ide.codeEditor.dto.FilePostRequest;
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

    // save
    @Transactional
    public FileResponse create(FilePostRequest filePostRequest){
        File file = File.builder()
                .name("hello, file")
                .language(filePostRequest.getLanguage())
                .content(filePostRequest.getContent())
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();

        return new FileResponse(fileRepository.save(file));
    }

    // update
    @Transactional
    public FileResponse save(FilePostRequest filePostRequest){
        Optional<File> file = fileRepository.findById(filePostRequest.getId());
        if(file.isEmpty()) throw new IllegalArgumentException("파일이 존재하지 않습니다.");

        file.get().setLanguage(filePostRequest.getLanguage());
        file.get().setContent(filePostRequest.getContent());
        file.get().setUpdateDateTime(LocalDateTime.now());

        return new FileResponse(fileRepository.update(file.get()));
    }

    // run
    public void run(){
        // TODO: 언어에 맞는 도커 이미지 실행 구현로직
    }
}
