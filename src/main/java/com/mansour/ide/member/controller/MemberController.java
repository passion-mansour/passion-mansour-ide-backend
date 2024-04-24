package com.mansour.ide.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mansour.ide.member.dto.MemberDTO;
import com.mansour.ide.member.dto.MemberTokensDTO;
import com.mansour.ide.member.model.Member;
import com.mansour.ide.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberTokensDTO> register(@RequestBody Member member) {
        try {
            MemberTokensDTO memberTokens = memberService.register(member);
            return ResponseEntity.ok(memberTokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String newPassword = body.get("newPassword");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        try {
            memberService.resetPassword(loginId, newPassword);
            return ResponseEntity.ok().body("Password reset successful.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginId = auth.getName();

        try {
            memberService.changePassword(loginId, oldPassword, newPassword);
            return ResponseEntity.ok().body("Password changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/check-loginId")
    public ResponseEntity<?> checkLoginIdAvailability(@RequestParam String loginId) {
        boolean isAvailable = memberService.isLoginIdAvailable(loginId);
        if (isAvailable) {
            return ResponseEntity.ok().body("Login ID is available.");
        } else {
            return ResponseEntity.badRequest().body("Login ID is already taken.");
        }
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNicknameAvailability(@RequestParam String nickName) {
        boolean isAvailable = memberService.isNicknameAvailable(nickName);
        if (isAvailable) {
            return ResponseEntity.ok().body("Nickname is available.");
        } else {
            return ResponseEntity.badRequest().body("Nickname is already taken.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        memberService.deleteByLoginId(username);
        SecurityContextHolder.clearContext(); // logout the user
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<MemberTokensDTO> refreshAccessToken(@RequestParam String refreshToken) {
        try {
            MemberTokensDTO memberTokens = memberService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(memberTokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            // or more appropriate status based on the
            // error
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext(); // effectively logs the user out
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Member member) {
        try {
            memberService.updateMemberDetails(member.getId(), member.getName(), member.getNickName());
            return ResponseEntity.ok().body("Profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update profile.");
        }
    }

    @GetMapping("/my-page")
    public ResponseEntity<MemberDTO> getMyPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginId = auth.getName(); // JWT에서 loginId 추출

        try {
            MemberDTO memberDTO = memberService.findMemberDetailsByLoginId(loginId);
            return ResponseEntity.ok(memberDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}