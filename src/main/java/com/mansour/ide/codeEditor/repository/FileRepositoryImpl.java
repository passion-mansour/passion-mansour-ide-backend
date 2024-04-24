package com.mansour.ide.codeEditor.repository;

import com.mansour.ide.codeEditor.model.File;
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
public class FileRepositoryImpl implements FileRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // JDBC 템플릿을 사용하기 위해 필요한 구현 : 데이터를 조회해올 때, 각 row를 하나씩 돌면서 자바 객체로 만들어주는 역할(어떤 객체일지는 개발자가 정의)
    private RowMapper<File> fileRowMapper() {
        return ((rs, rowNum) -> {
            File file= new File();
            file.setId(rs.getLong("file.id"));
            file.setName(rs.getString("file.name"));
            file.setContent(rs.getString("file.content"));
            file.setLanguage(rs.getString("file.language"));

            String dateString = rs.getString("file.createdDt").replace(' ', 'T');
            LocalDateTime localDateTime = LocalDateTime.parse(dateString);
            file.setCreateDateTime(Timestamp.valueOf(localDateTime));

            dateString = rs.getString("file.updatedDt").replace(' ', 'T');
            localDateTime = LocalDateTime.parse(dateString);
            file.setUpdateDateTime(Timestamp.valueOf(localDateTime));

            return file;
        });
    }

    @Override
    public File save(File file) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("file").usingGeneratedKeyColumns("id");

        log.info("file name save = {}", file.getName());
        log.info("file content save = {}", file.getContent());
        log.info("file language save = {}", file.getLanguage());
        log.info("file createDt save = {}", file.getCreateDateTime());
        log.info("file updatedDt save = {}", file.getUpdateDateTime());

        Map<String, Object> params = new HashMap<>();
        params.put("name", file.getName());
        params.put("content", file.getContent());
        params.put("language", file.getLanguage());
        params.put("createdDt", Timestamp.from(Instant.now()));
        params.put("updatedDt", Timestamp.from(Instant.now()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        file.setId(key.longValue());

        return file;
    }

    @Override
    public File update(File file) {
        String sql = "UPDATE file SET language = ?, content = ?, updatedDt = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, file.getLanguage(), file.getContent(), file.getId());

        return file;
    }

    @Override
    public List<File> getAllFile() {
        return jdbcTemplate.query("SELECT * FROM file", fileRowMapper());
    }

    @Override
    public Optional<File> findById(Long id) {
        List<File> result = jdbcTemplate.query("SELECT * FROM file WHERE id = ?", fileRowMapper(), id);
        return result.stream().findAny(); // findAny하면 Optional로 반환되서 이렇게 사용하면 좋다.
    }
    @Override
    public Optional<File> getFileByName(String name) {
        List<File> result = jdbcTemplate.query("SELECT * FROM file WHERE name = ?", fileRowMapper(), name);
        return result.stream().findAny();
    }
}