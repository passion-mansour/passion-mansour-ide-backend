package com.mansour.ide.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT")
public class Project {
    private Long id;
    private Long hostId;
    private String pw;
    private String title;
    private String tagLanguage; // 태그용 언어
    private int maxUser;
    private Boolean isLock;
    private Boolean isEnd;
    private Timestamp createDateTime;
    private Timestamp endDateTime;

    private Long fileId;
}