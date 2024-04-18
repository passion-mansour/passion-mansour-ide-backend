package com.mansour.ide.board.repository;

import com.mansour.ide.enums.Language;
import com.mansour.ide.board.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Project save(Project project) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("project").usingGeneratedKeyColumns("id");

        // insert할때 전해주는 파라미터
        Map<String, Object> params = new HashMap<>();
        params.put("id", project.getId());
        params.put("pw", project.getPw());
        params.put("title", project.getTitle());
        params.put("maxUser", project.getMaxUser());
        params.put("isLock", project.isLock());
        params.put("isEnd", project.isEnd());
        params.put("language", project.getLanguage());
        params.put("createDT", project.getCreateDateTime());
        params.put("updateDT", project.getUpdateDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        project.setId(key.longValue());

        return project;
    }

    public void update() {
        // TODO: 구현
    }

    public void delete() {
        // TODO: 구현
    }

    public List<Project> getAll() {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project", projectRowMapper());
        return result;
    }

    public Optional<Project> getById(Long id) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE project_id = ?", projectRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<Project> getByTitle(String title) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE project_title = ?", projectRowMapper(), title);
        return result.stream().findAny();
    }

    // JDBC 템플릿을 사용하기 위해 필요한 구현 : 데이터를 조회해올 때, 각 row를 하나씩 돌면서 자바 객체로 만들어주는 역할(어떤 객체일지는 개발자가 정의)
    private RowMapper<Project> projectRowMapper() {
        return ((rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getLong("project.id"));
            project.setPw(rs.getString("project.pw"));
            project.setTitle(rs.getString("project.title"));
            project.setMaxUser(rs.getInt("project.maxUser"));
            project.setLock(rs.getBoolean("project.isLock"));
            project.setEnd(rs.getBoolean("project.isEnd"));
            project.setLanguage((Language) rs.getObject("project.language"));
            project.setCreateDateTime((LocalDateTime) rs.getObject("project.createDT"));
            project.setUpdateDateTime((LocalDateTime) rs.getObject("project.updateDT"));

            return project;
        });
    }
}
