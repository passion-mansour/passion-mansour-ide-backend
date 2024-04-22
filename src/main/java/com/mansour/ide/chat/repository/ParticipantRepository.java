package com.mansour.ide.chat.repository;

import com.mansour.ide.chat.model.Participant;
import com.mansour.ide.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ParticipantRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Participant save(Participant participant) {
        String sql = "INSERT INTO participant (project_id, user_id, permission) VALUES (:projectId, :userId, :permission)";

        MapSqlParameterSource parameter = new MapSqlParameterSource()
            .addValue("projectId", participant.getProjectId())
            .addValue("userId", participant.getUserId())
            .addValue("permission", false);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameter, keyHolder);

        participant.setParticipantId(keyHolder.getKey().longValue());

        log.info("participant {}", participant);

        return participant;
    }

    public Optional<Member> getUserById(Long id) {
        String sql = "SELECT * FROM member WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Member> members = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Member.class));
        if (members.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(members.get(0));
    }

    // 프로젝트에 참가한 유저 아이디 리스트
    public List<Long> findUserIdsByProjectId(Long projectId) {
        String sql = "SELECT userId FROM participant WHERE projectId = :projectId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("projectId", projectId);

        return namedParameterJdbcTemplate.queryForList(sql, params, Long.class);
    }

    // 유저의 참가 아이디 조회
    public Long findUserIdById(Long participantId) {
        String sql = "SELECT user_id FROM participant WHERE participant_id = :participantId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("participantId", participantId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);

    }


}
