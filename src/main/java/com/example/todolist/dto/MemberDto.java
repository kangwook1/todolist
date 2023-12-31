package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String nickname;


    public Member signupToEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
