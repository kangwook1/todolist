package com.example.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindPasswordReqDto {
    private String loginId;
    private String email;
}
