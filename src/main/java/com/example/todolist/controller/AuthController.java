package com.example.todolist.controller;

import com.example.todolist.dto.request.RefreshTokenReqDto;
import com.example.todolist.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request){
        String refreshToken=request.getHeader("Refresh-Token");
        String accessToken=authService.reissueAccessToken(refreshToken);
    return ResponseEntity.ok(200);
    }


}
