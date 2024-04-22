package com.mansour.ide.codeEditor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILE")
public class File {
    @Id
    @Column("file_id")
    private Long id;
    private String name;
    private String content;
    private String language;
    private Timestamp createDateTime;
    private Timestamp updateDateTime;

}
