package com.mansour.ide.chat.repository;

import com.mansour.ide.chat.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatRepository {

    private final NamedParameterJdbcTemplate template;

    private RowMapper<Message> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Message.class);
    }

    public Message save(Message message) {
        final String sql = "INSERT INTO message (projectId, userId, message, createdAt) " +
            "VALUES (:projectId, :userId, :message, :createdAt)";

        MapSqlParameterSource parameter = new MapSqlParameterSource()
            .addValue("projectId", message.getProjectId())
            .addValue("userId", message.getUserId())
            .addValue("message", message.getMessage())
            .addValue("createdAt", message.getCreatedAt());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, parameter, keyHolder);

        message.setMessageId(keyHolder.getKey().longValue());

        log.info("chatMessage {}", message);
        return message;
    }

    // TODO: 검색기능
}
