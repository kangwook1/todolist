package com.example.todolist.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//권한이 없는 사용자에 대한 예외처리
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CustomException ex=new CustomException(ErrorCode.UNAUTHORIZED_USER);

        //응답 상태코드에 status를 json으로 보냄.
        response.setStatus(ex.getErrorCode().getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //json으로 보낼 body에 해당하는 포맷을 설정함.
        // //response.getWriter().write(...)는 내부적으로 flush()가 발생해서 따로 호출할 필요 없음.
        response.getWriter().write(String.format("{\"status\": \"%s\", \"message\": \"%s\"}",
                ex.getErrorCode().getStatus(),ex.getErrorCode().getMessage()));
    }
}