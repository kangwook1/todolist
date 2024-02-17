package com.example.todolist.exception;

import lombok.Getter;

@Getter
/*
    1. 컴파일 시 발생하는 예외 처리를 구현할 경우에는 Exception을 상속받아 구현
    2. 실행 시 발생하는 예외 처리를 구현할 경우에는 RuntimeException을 상속받아 구현
 */
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        this.errorCode=errorCode;
    }

}
