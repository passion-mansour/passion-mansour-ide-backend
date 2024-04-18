package com.mansour.ide.board.model;

import com.mansour.ide.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private Long id;
    private String pw;
    private String title;
    private int maxUser;
    private boolean isLock;
    private boolean isEnd;
    private Language language; // 언어 태그
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}
