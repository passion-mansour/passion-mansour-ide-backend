package com.mansour.ide.board.dto;

import com.mansour.ide.board.model.Project;
import com.mansour.ide.member.dto.MemberDTO;
import com.mansour.ide.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String pw;
    private String title;
    private String language; // 언어 태그
    private int maxUser;
    private Boolean isLock;
    private Boolean isEnd;
    private Timestamp createDateTime;
    private Timestamp updateDateTime;

    private Long fileId;
    private MemberDTO host;

    // Static Factory Method
    public static ProjectResponse from(Project project){
        MemberService memberService = new MemberService();

        return new ProjectResponse(
                project.getId(),
                project.getPw(),
                project.getTitle(),
                project.getTagLanguage(),
                project.getMaxUser(),
                project.getIsLock(),
                project.getIsEnd(),
                project.getCreateDateTime(),
                project.getEndDateTime(),
                project.getFileId(),
                memberService.findMemberDetailsById(project.getHostId())
                );
    }
}
