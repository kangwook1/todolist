package com.example.todolist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenResDto {
    private String token;

    @Builder
    public JwtTokenResDto(String token) {
        this.token = token;
    }
}