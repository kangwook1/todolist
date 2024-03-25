package com.example.todolist.security;

import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.AccessTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


/*
       Spring Security의 인증, 접근 제어가 Filter로 구현되고 이런 인증, 접근 제어는 RequestDispatcher 클래스에 의해서 다른 서블릿으로 dispatch 된다.
       이때, 이동할 서블릿에 도착하기 전에 다시 한번 filter chain을 거치며 필터가 두 번 실행되는 현상이 발생할 수 있다.
       GenericFilterBean을 자바 빈으로 등록하면 해당 필터가 두번 실행되는 문제가 발생한다.
       이를 해결하기 위해서는 GenericFilterBean이 아닌 OncePerRequestFilter을 상속받으면 된다.
*/
 @Component
 @Log4j2
 @RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccessTokenRepository accessTokenRepository;

    // url이 포함된 경로에 대해서는 JWT 필터를 적용하지 않고 무시
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {"/member", "/auth/reissue", "/mail"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }


    @Override
    //OncePerRequestFilter을 상속받으면 doFilterInternal을 써야한다.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken=jwtTokenProvider.resolveAccessToken(request);

        if (!accessToken.startsWith("Bearer ")) {
           filterChain.doFilter(request,response);
           return;
        }
        accessToken = accessToken.substring(7);  // "Bearer "를 제거하여 실제 토큰만 추출


        if(jwtTokenProvider.validateToken(accessToken)){
            //로그아웃 검증(access 토큰이 있으면 로그아웃한 것이므로 불통과)
            if(accessTokenRepository.existsById(accessToken)){
                throw new CustomException(ErrorCode.USER_LOGOUT);
            }
            else{
                Authentication authentication=jwtTokenProvider.getAuthentication(accessToken);
            /*
            contextHolder에 인증객체저장. 인가과정을 거친 후 해당 인증객체는 삭제된다.
            그다음,http session에 authentication을 저장한다.//일반적인 세션 작동(jwt는 안이럼)
            이후, SecurityContextPersistenceFilter에서 authentication을 삭제한다.//securityContext를 제거
            jwt는 stateless 설정을 했기때문에 jSessionId를 관리하지 않아서, 세션은 응답이 끝나면 사라지게 된다.
            메모리 저장소에 용도로 잠시 사용하고 버리는 용도이다.
            session을 사용하지 않기로 돼있지만 권한처리를 위해 어쩔 수 없이 사용한다.
            */
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        else{
            // Access Token이 유효하지 않을 때 401 에러와 함께 메시지를 JSON으로 클라이언트에 전송
            throw new  CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        filterChain.doFilter(request, response);
    }
}

