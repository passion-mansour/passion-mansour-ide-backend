package com.mansour.ide.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTokensDTO {
    private MemberDTO member;
    private String accessToken;
    private String refreshToken;
}
