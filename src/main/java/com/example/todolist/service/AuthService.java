package com.example.todolist.service;

import com.example.todolist.domain.AccessToken;
import com.example.todolist.domain.Member;
import com.example.todolist.domain.RefreshToken;
import com.example.todolist.repository.AccessTokenRepository;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.RefreshTokenRepository;
import com.example.todolist.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    /*
        1.요청 헤더에서 가져온 refreshToken의 유효성을 검증한다.
        2.검증이 됐으면, Redis에서 key를 loginId로 갖고 있는 refreshToken을 조회한다.
        3.redis에서 조회한 refreshToken과 요청으로 받은 refreshToken이 동일한지 검증한다.
        4.동일하다면 loginId로 유저정보를 조회해 accessToken을 발급한다.
    */

    public String reissueAccessToken(String refreshToken){
        // 1
        if(refreshToken!=null && jwtTokenProvider.validateToken(refreshToken)){
            String loginId= jwtTokenProvider.getUserLoginId(refreshToken);
            Optional<RefreshToken> redisToken= refreshTokenRepository.findById(loginId);
            if(redisToken.isEmpty())
                throw new IllegalArgumentException("refreshToken이 유효하지 않습니다.");
            //2
            RefreshToken redisRefreshToken=redisToken.get();
            //3
            if(refreshToken.equals(redisRefreshToken.getRefreshToken())){
                Member member=memberRepository.findByLoginId(loginId).get();
                //4
                return jwtTokenProvider.createAccessToken(member.getLoginId(),member.getRole().name());
            }
            else{
                throw new IllegalArgumentException("refreshToken이 동일하지 않습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("refreshToken이 유효하지 않습니다.");
        }
    }

    /*
        1.요청 헤더에서 가져온 accessToken의 유효성을 검증한다.(JwtAuthenticationFilter에서 이미 검증함. 여기서까지 할 필요 x)
        2.검증이 됐으면, accessToken에서 loginId를 가져온다.
        3.loginId를 키 값으로 가지는 refreshToken을 redis에서 조회하고 있다면 삭제한다.
        4.acceessToken을 키값으로 저장하고 남은 유효기간을 설정한다.
    */
    public void logout(String accessToken){
            //2
            String loginId= jwtTokenProvider.getUserLoginId(accessToken);
            // 3
            refreshTokenRepository.deleteById(loginId);
            // 4
            Long pastExpiration=jwtTokenProvider.getExpiration(accessToken);
            // 남은 만료 기한= 만료 기한 - 현재 시간
            Long expiration= jwtTokenProvider.getExpiration(accessToken)-new Date().getTime();
            AccessToken redisAccessToken=new AccessToken(accessToken,expiration);
            accessTokenRepository.save(redisAccessToken);

    }
}
