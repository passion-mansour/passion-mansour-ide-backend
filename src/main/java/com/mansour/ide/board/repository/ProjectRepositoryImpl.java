package com.mansour.ide.board.repository;

import com.mansour.ide.board.model.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // JDBC 템플릿을 사용하기 위해 필요한 구현 : 데이터를 조회해올 때, 각 row를 하나씩 돌면서 자바 객체로 만들어주는 역할(어떤 객체일지는 개발자가 정의)
    private RowMapper<Project> projectRowMapper() {
        return ((rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getLong("project.id"));
            project.setHostId(rs.getLong("project.hostId"));
            project.setPw(rs.getString("project.pw"));
            project.setTitle(rs.getString("project.title"));
            project.setTagLanguage(rs.getString("project.tagLanguage"));
            project.setMaxUser(rs.getInt("project.maxUser"));
            project.setIsLock(rs.getBoolean("project.isLock"));
            project.setIsEnd(rs.getBoolean("project.isEnd"));

            // 1. 문자열로 정재된 값 받기
            String dateString = rs.getString("project.createDt").replace(' ', 'T');
            // 2. 문자열 값을 LocalDateTime으로 변환
            LocalDateTime localDateTime = LocalDateTime.parse(dateString);
            // 3. LocalDateTime 값을 Timestamp로 변환
            project.setCreateDateTime(Timestamp.valueOf(localDateTime));

            dateString = rs.getString("project.endDt").replace(' ', 'T');;
            localDateTime = LocalDateTime.parse(dateString);
            project.setEndDateTime(Timestamp.valueOf(localDateTime));

            project.setFileId(rs.getLong("project.fileId"));

            log.info("row mapper 종료");
            return project;
        });
    }

    @Override
    public Project save(Project project) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("project").usingGeneratedKeyColumns("id");

        // insert할때 전해주는 파라미터
        Map<String, Object> params = new HashMap<>();
        params.put("hostId", project.getHostId());
        params.put("pw", project.getPw());
        params.put("title", project.getTitle());
        params.put("tagLanguage", project.getTagLanguage());
        params.put("maxUser", project.getMaxUser());
        params.put("isLock", project.getIsLock());
        params.put("isEnd", project.getIsEnd());
        params.put("createDt", Timestamp.from(Instant.now()));
        params.put("endDt", Timestamp.from(Instant.now()));
        params.put("fileId", project.getFileId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        project.setId(key.longValue());

        return project;
    }

    @Override
    public Project updateFileId(Project project, Long fileId) {
        String sql = "UPDATE project SET fileId = ? WHERE id = ?";
        jdbcTemplate.update(sql, fileId, project.getId());

        return project;
    }

    @Override
    public Project updateEndStatus(Project project, Boolean endStatus) {
        String sql = "UPDATE project SET isEnd = ? WHERE id = ?";
        jdbcTemplate.update(sql, endStatus, project.getId());

        return project;
    }

    @Override
    public List<Project> getAll() {
        return jdbcTemplate.query("SELECT * FROM project", projectRowMapper());
    }

    @Override
    public Optional<Project> getById(Long id) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE id = ?", projectRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Project> getByTitle(String title) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE title = ?", projectRowMapper(), title);
        return result.stream().findAny();
    }

    @Override
    public List<Project> getByEndStatus(boolean isEnd) {
        return jdbcTemplate.query("SELECT * FROM project WHERE isEnd = ?", projectRowMapper(), isEnd);
    }
}
