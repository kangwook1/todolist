package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.RefreshToken;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.RefreshTokenRepository;
import com.example.todolist.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /*
        1.요청 헤더에서 가져온 refreshToken을 검증한다.
        2.검증이 됐으면, Redis에서 key를 loginId로 갖고 있는 refreshToken을 조회한다.
        3.redis에서 조회한 refreshToken과 요청으로 받은 refreshToken이 동일한지 검증한다.
        4.동일하다면 loginId로 유저정보를 조회해 accessToken을 발급한다.
    */

    public String reissueAccessToken(String refreshToken){
        if(refreshToken!=null && jwtTokenProvider.validateToken(refreshToken)){
            String loginId= jwtTokenProvider.getUserLoginId(refreshToken);
            Optional<RefreshToken> redisToken=refreshTokenRepository.findById(loginId);
            if(redisToken.isEmpty())
                throw new IllegalArgumentException("refreshToken이 유효하지 않습니다.");
            RefreshToken redisRefreshToken=redisToken.get();
            if(refreshToken.equals(redisRefreshToken.getRefreshToken())){
                Member member=memberRepository.findByLoginId(loginId).get();
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
}
