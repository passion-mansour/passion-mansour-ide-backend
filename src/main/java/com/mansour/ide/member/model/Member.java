package com.mansour.ide.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private Long id;       // 유저 ID
    private String name;   // 유저 이름
    private String nickName; // 유저 닉네임
    private String loginId; // 로그인 ID
    private String password; // 로그인 비밀번호

    
}
