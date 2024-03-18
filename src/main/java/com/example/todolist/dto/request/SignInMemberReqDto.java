package com.example.todolist.dto.request;

import com.example.todolist.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor
public class SignInMemberReqDto {
    private String loginId;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}
