package com.example.todolist.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String status;
    private String message;

    @Builder
    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
