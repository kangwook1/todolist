package com.example.todolist.dto.request;

import com.example.todolist.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberSignUpReqDto {
    private String loginId;
    private String password;
    private String email;
    private String nickname;


    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
