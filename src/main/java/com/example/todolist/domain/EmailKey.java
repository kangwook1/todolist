package com.example.todolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Email",timeToLive = 1  * 5 * 60 * 1000L) //5분
@Getter
public class EmailKey {
    @Id
    private String key;

    public EmailKey(String key) {
        this.key = key;
    }
}
