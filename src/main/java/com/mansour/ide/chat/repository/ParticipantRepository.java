package com.mansour.ide.chat.repository;

import com.mansour.ide.chat.model.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ParticipantRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(Participant participant) {
        String sql = "INSERT INTO participants (participant_id, project_id, user_id, permission) VALUES (:participantId, :projectId, :userId, :permission)";
        Map<String, Object> params = new HashMap<>();
        params.put("participantId", participant.getParticipantId());
        params.put("projectId", participant.getProjectId());
        params.put("userId", participant.getUserId());
        params.put("permission", participant.getPermission());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }

    // 프로젝트에 참가한 유저 아이디 리스트
    public List<Long> findUserIdsByProjectId(Long projectId) {
        String sql = "SELECT userId FROM participant WHERE projectId = :projectId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("projectId", projectId);

        return namedParameterJdbcTemplate.queryForList(sql, params, Long.class);
    }

    // 참가 유저 아이디 조회
    public Long findUserIdById(Long participantId) {
        String sql = "SELECT user_id FROM participant WHERE participant_id = :participantId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("participantId", participantId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);

    }

}
