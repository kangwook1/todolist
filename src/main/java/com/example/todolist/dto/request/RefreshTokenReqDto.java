package com.example.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenReqDto {

    private String refreshToken;
}
