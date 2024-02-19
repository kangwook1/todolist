package com.example.todolist.security;

import com.example.todolist.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (CustomException ex) {
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            sendErrorResponse(response,ex);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, CustomException ex) throws IOException {
        //응답 상태코드에 status를 json으로 보냄.
        response.setStatus(ex.getErrorCode().getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //json으로 보낼 body에 해당하는 포맷을 설정함.
        //response.getWriter().write(...)는 내부적으로 flush()가 발생해서 따로 호출할 필요 없음.
        response.getWriter().write(String.format("{\"status\": \"%s\", \"message\": \"%s\"}",
                ex.getErrorCode().getStatus(),ex.getErrorCode().getMessage()));
    }
}
