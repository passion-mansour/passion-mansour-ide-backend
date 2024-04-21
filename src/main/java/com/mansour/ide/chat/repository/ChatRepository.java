package com.mansour.ide.chat.repository;

import com.mansour.ide.chat.model.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatRepository {

    private final NamedParameterJdbcTemplate template;

    private RowMapper<Messages> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Messages.class);
    }

    public Messages save(Messages messages) {
        final String sql = "INSERT INTO messages (project_id, participant_id, message, create_at) " +
            "VALUES (:projectId, :participantId, :message, :createAt)";

        MapSqlParameterSource parameter = new MapSqlParameterSource()
            .addValue("projectId", messages.getProjectId())
            .addValue("participantId", messages.getParticipantId())
            .addValue("message", messages.getMessage())
            .addValue("createAt", LocalDateTime.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, parameter, keyHolder);

        messages.setMessageId(keyHolder.getKey().longValue());

        log.info("chatMessage {}", messages);
        return messages;
    }

    // TODO: 검색기능
}
