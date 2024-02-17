package com.example.todolist.controller;

import com.example.todolist.dto.request.RefreshTokenReqDto;
import com.example.todolist.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request){
        String refreshToken=request.getHeader("Refresh-Token");
        String accessToken=authService.reissueAccessToken(refreshToken);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String accessToken=request.getHeader("Authorization").substring(7);
        authService.logout(accessToken);
        return ResponseEntity.ok()
                .build();
    }


}
