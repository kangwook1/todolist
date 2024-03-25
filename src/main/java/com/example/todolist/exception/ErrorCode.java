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
    INVALID_EMAIL_KEY(HttpStatus.BAD_REQUEST,"인증 키가 틀렸습니다."),

    /* 401 UNAUTHORIZED : 비인증 사용자 */
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"refresh 토큰이 유효하지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"access 토큰이 유효하지 않습니다."),
    USER_LOGOUT(HttpStatus.UNAUTHORIZED,"로그아웃한 유저입니다. 다시 로그인 해주세요."),

    /* 403 FORBIDDEN : 권한이 없는 사용자 */
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN,"권한이 없는 유저입니다."),

    /* 404 NOT_FOUNT : 존재하지 않는 리소스 */
    NOT_EXIST_USER(HttpStatus.NOT_FOUND,"존재하지 않는 유저입니다."),

    /* 409 CONFLICT : 리소스 충돌 */
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT,"이미 존재하는 아이디입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT,"이미 존재하는 이메일입니다.");


    private final HttpStatus status;
    private final String message;
}
