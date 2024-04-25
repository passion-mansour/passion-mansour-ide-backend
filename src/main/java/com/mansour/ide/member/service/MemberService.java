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

    private MemberDTO convertToMemberDTO(Member member) {
        if (member == null) {
            return null; // or throw an exception, based on your design decision
        }
        return new MemberDTO(
                member.getId(),
                member.getName(),
                member.getNickName(),
                member.getLoginId());
    }

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

    public MemberTokensDTO refreshAccessToken(String refreshToken) throws Exception {
        if (!jwtTokenUtil.validateRefreshToken(refreshToken)) {
            throw new Exception("Invalid refresh token.");
        }

        String loginId = jwtTokenUtil.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new Exception("Member not found");
        }

        MemberDTO memberDTO = convertToMemberDTO(member);

        String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);

        return new MemberTokensDTO(memberDTO, newAccessToken, refreshToken);
    }

    public void deleteByLoginId(String loginId) {
        memberRepository.deleteByLoginId(loginId);
    }

    public boolean isLoginIdAvailable(String loginId) {
        return !memberRepository.existsByLoginId(loginId);
    }

    public boolean isNicknameAvailable(String nickName) {
        return !memberRepository.existsByNickName(nickName);
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

    public MemberDTO findMemberDetailsById(Long id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new MemberDTO(member.getId(), member.getName(), member.getNickName(), member.getLoginId());
    }

    public void updateMemberDetails(Long memberId, String name, String nickName) {
        memberRepository.updateMemberDetails(memberId, name, nickName);
    }
}
