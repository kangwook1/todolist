package com.example.todolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

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
