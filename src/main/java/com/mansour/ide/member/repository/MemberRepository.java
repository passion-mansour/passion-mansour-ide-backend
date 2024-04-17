package com.mansour.ide.member.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO member (name, nickName, loginId, password) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getName());
            ps.setString(2, member.getNickName());
            ps.setString(3, member.getLoginId());
            ps.setString(4, member.getPassword());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long generatedId = (key != null) ? key.longValue() : null;
        if (generatedId != null) {
            member.setId(generatedId);
        } else {
            throw new RuntimeException("Failed to retrieve auto-generated ID");
        }
        member.setId(generatedId);

        return member; 
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
}
