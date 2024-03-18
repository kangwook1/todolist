package com.example.todolist.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginJwtTokenResDto {
    private String accessToken;
    private String refreshToken;


    @Builder
    public LoginJwtTokenResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
