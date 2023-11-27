package com.example.todolist.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
 /*
        GenericFilterBean을 자바 빈으로 등록하면 해당 필터가 두번 실행되는 문제가 발생한다.
        이를 해결하기 위해서는 GenericFilterBean이 아닌 OncePerRequestFilter을 상속받으면 된다.
    */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=jwtTokenProvider.resolveToken(request);

        if(token !=null && jwtTokenProvider.validateToken(token)){
            Authentication authentication=jwtTokenProvider.getAuthentication(token);
            // contextHolder에 인증객체저장. 인가과정을 거친 후 해당 인증객체는 삭제된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
