package com.mansour.ide.member.service;

import com.mansour.ide.common.security.JwtTokenUtil;
import com.mansour.ide.member.dto.MemberDTO;
import com.mansour.ide.member.dto.MemberTokensDTO;
import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberTokensDTO register(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member savedMember = memberRepository.save(member);

        // MemberDTO 생성
        MemberDTO memberDTO = new MemberDTO(savedMember.getId(), savedMember.getName(), savedMember.getNickName(),
                savedMember.getLoginId());

        // 토큰 생성
        final UserDetails userDetails = userDetailsService.loadUserByUsername(savedMember.getLoginId());
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        return new MemberTokensDTO(memberDTO, accessToken, refreshToken);
    }

    public void deleteByLoginId(String loginId) {
        memberRepository.deleteByLoginId(loginId);
    }

    public void resetPassword(String loginId, String newPassword) {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void changePassword(String loginId, String oldPassword, String newPassword) {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new IllegalArgumentException("Old password does not match.");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public MemberDTO findMemberDetailsByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new MemberDTO(member.getId(), member.getName(), member.getNickName(), member.getLoginId());
    }
}
