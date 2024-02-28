package com.example.todolist.dto.request;

import com.example.todolist.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberSignUpReqDto {

    @NotBlank
    @Pattern(message = "아이디는 5~10자의 영어,숫자로 이루어져야 합니다.", regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{5,10}$")
    private String loginId;

    @NotBlank
    @Pattern(message = "비밀번호는 8자 이상의 영어, 숫자, 특수문자로 이루어져야 합니다."
            , regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;

    @NotBlank
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비어있으면 안됩니다.")
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
