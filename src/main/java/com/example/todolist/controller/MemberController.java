package com.example.todolist.controller;

import com.example.todolist.dto.request.SignUpMemberReqDto;
import com.example.todolist.dto.response.LoginJwtTokenResDto;
import com.example.todolist.dto.request.SignInMemberReqDto;
import com.example.todolist.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
//@requestbody를 써야 raw데이터로 json을 보낸다.
//@requestbody를 생략하면 form형식으로 데이터를 받는다.
public class MemberController {
    private final MemberService memberService;

    /*
        모든 요청은 프론트 컨트롤러인 디스패처 서블릿을 통해 컨트롤러로 전달된다.
        @valid는 controller 메소드의 객체를 만들어주는 ArgumentResolver에 의해 처리된다.
        따라서 기본적으로 컨트롤러에서만 작동한다.
        다른 계층에서 파라미터를 검증하기 위해서는 @validated를 써야한다.
     */
    @PostMapping(value = "/join")
    public ResponseEntity<?> join(@RequestBody @Valid SignUpMemberReqDto signUpMemberReqDto){
         memberService.join(signUpMemberReqDto);
         return ResponseEntity.ok(signUpMemberReqDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody SignInMemberReqDto signInMemberReqDto){
        LoginJwtTokenResDto jwtToken=memberService.login(signInMemberReqDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.getAccessToken())
                .header("Refresh-Token", jwtToken.getRefreshToken())
                .body(jwtToken);
    }
}
