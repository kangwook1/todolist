package com.example.todolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

//Redis Lettuce를 사용하기위해 @RedisHash 어노테이션 써야한다.
//value는 redis key 값으로 사용된다
//redis 저장소의 key로는 {value}:{@Id 어노테이션을 붙여준 값}이 저장된다.
@RedisHash(value = "Token",timeToLive = 1 * 24 * 60 * 60 * 1000L) //1일
@Getter
public class RefreshToken {

    //java.persistence.id가 아닌 opg.springframework.data.annotation.Id 를 import해야한다.
    //Redis에 저장하기 떄문에 jpa에 의존하지 않는다.
    @Id
    private String loginId;
    private String refreshToken;

    public RefreshToken(String loginId, String refreshToken) {
        this.loginId = loginId;
        this.refreshToken = refreshToken;
    }
}
