package com.example.todolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

//로그아웃 하면 Redis에 access 토큰 생성,만료기한 동안 인증 못함(아니면 로그인 다시 해야함)
@RedisHash(value = "Logout")
@Getter
public class AccessToken {

    @Id
    private String accessToken;

    @TimeToLive
    private Long expiration;

    public AccessToken(String accessToken, Long expiration) {
        this.accessToken = accessToken;
        this.expiration = expiration;
    }
}
