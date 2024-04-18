package com.mansour.ide.board.service;

import com.mansour.ide.board.model.Project;
import com.mansour.ide.board.repository.BoardRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final BoardRepositoryImpl projectRepository;
    private final List<Project> board = new ArrayList<>();

    // 프로젝트 생성
    // 폴더와 파일 각각 하나씩 생성
    // 호스트 임명


    // 프로젝트 입장


    // 프로젝트 퇴장
}
