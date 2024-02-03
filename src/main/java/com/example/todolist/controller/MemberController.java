package com.example.todolist.controller;

import com.example.todolist.dto.response.JwtTokenResDto;
import com.example.todolist.dto.request.MemberSignInReqDto;
import com.example.todolist.dto.request.MemberSignUpReqDto;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*@GetMapping(value = "/signup")
    public String signup(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "signup";
    }
    */
    @PostMapping(value = "/join")
    public ResponseEntity<?> join(@RequestBody MemberSignUpReqDto memberSignUpReqDto){
         memberService.join(memberSignUpReqDto);
         return ResponseEntity.ok(memberSignUpReqDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody MemberSignInReqDto memberSignInReqDto){
        JwtTokenResDto jwtToken=memberService.login(memberSignInReqDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.getAccessToken())
                .header("Refresh-Token", jwtToken.getRefreshToken())
                .body(jwtToken);
    }
}
