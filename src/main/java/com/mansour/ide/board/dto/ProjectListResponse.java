package com.mansour.ide.board.dto;

import com.mansour.ide.board.model.Project;
import com.mansour.ide.member.dto.MemberDTO;
import com.mansour.ide.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectListResponse {
    private List<ProjectResponse> projects;

}
