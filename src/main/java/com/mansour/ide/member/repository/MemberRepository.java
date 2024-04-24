package com.mansour.ide.member.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mansour.ide.member.model.Member;

@Repository
public class MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        member.setNickName(rs.getString("nickName"));
        member.setLoginId(rs.getString("loginId"));
        member.setPassword(rs.getString("password"));
        return member;
    };

    public Member save(Member member) {
        if (member.getId() == null) {
            // New member insertion
            return insertMember(member);
        } else {
            // Existing member update
            updateMember(member);
            return member;
        }
    }

    private Member insertMember(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO member (name, nickName, loginId, password) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getName());
            ps.setString(2, member.getNickName());
            ps.setString(3, member.getLoginId());
            ps.setString(4, member.getPassword());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            member.setId(key.longValue());
        } else {
            throw new RuntimeException("Failed to retrieve auto-generated ID");
        }
        return member;
    }

    private void updateMember(Member member) {
        String sql = "UPDATE member SET name = ?, nickName = ?, password = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, member.getName(), member.getNickName(), member.getPassword(),
                member.getId());
        if (updated != 1) {
            throw new RuntimeException("No member found with id = " + member.getId());
        }
    }

    public void deleteByLoginId(String loginId) {
        jdbcTemplate.update("DELETE FROM member WHERE loginId = ?", loginId);
    }

    public Member findByLoginId(String loginId) {
        List<Member> members = jdbcTemplate.query("SELECT * FROM member WHERE loginId = ?", memberRowMapper, loginId);
        // TODO: Exception handling
        if (members.isEmpty()) {
            return null;
        }
        return members.get(0);
    }

    public Member findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper, id);
    }

    public boolean existsByNickName(String nickName) {
        String sql = "SELECT COUNT(*) FROM member WHERE nickName = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nickName);
        return count != null && count > 0;
    }

    public boolean existsByLoginId(String loginId) {
        String sql = "SELECT COUNT(*) FROM member WHERE loginId = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
        return count != null && count > 0;
    }

    public void updateMemberDetails(Long id, String name, String nickName) {
        jdbcTemplate.update(
                "UPDATE member SET name = ?, nickName = ? WHERE id = ?",
                name, nickName, id);
    }
}
