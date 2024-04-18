package com.mansour.ide.member.service;

import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.repository.MemberRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member register(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    public void deleteByLoginId(String loginId) {
        memberRepository.deleteByLoginId(loginId);
    }
}
