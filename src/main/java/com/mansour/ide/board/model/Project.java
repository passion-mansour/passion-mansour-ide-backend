package com.mansour.ide.board.model;

<<<<<<< HEAD
import com.mansour.ide.enums.Language;
=======
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.annotation.Id;

=======
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
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
=======
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
>>>>>>> fca6a491da7e671a71d5b938a32b254b35235e58
