package com.mansour.ide.member.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        jdbcTemplate.update("INSERT INTO member (name, nickName, loginId, password) VALUES (?, ?, ?, ?)",
                member.getName(), member.getNickName(), member.getLoginId(), member.getPassword());
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
