package com.example.todolist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
/*
    Enum은 본질적으로 불변이기 때문에 멤버 변수들 또한 final로 지정해줘야 한다.
    상수에 값을 지정할 때, 그에 맞는 멤버 필드와 생성자를 선언해야 한다, getter도 있으면 좋다.
    Enum은 생성자가 private이다.
 */
public enum ErrorCode {
    // 생성자를 호출해서 값을 할당하는 방식

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST,"아이디가 틀렸습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 틀렸습니다."),
    /* 401 UNAUTHORIZED : 비인증 사용자 */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"토큰이 유효하지 않습니다."),

    /* 409 CONFLICT : 리소스 충돌 */
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT,"아이디가 중복입니다.");


    private final HttpStatus status;
    private final String message;
}
