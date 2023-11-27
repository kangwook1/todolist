package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberSignInReqDto {
    private String loginId;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}
