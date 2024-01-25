package com.example.todolist.controller;

import com.example.todolist.dto.JwtTokenResDto;
import com.example.todolist.dto.MemberSignInReqDto;
import com.example.todolist.dto.MemberSignUpReqDto;
import com.example.todolist.service.MemberService;
import groovy.util.logging.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*@GetMapping(value = "/signup")
    public String signup(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "signup";
    }
    */
    @PostMapping(value = "/join")
    public ResponseEntity<?> join(MemberSignUpReqDto memberSignUpReqDto){
         memberService.join(memberSignUpReqDto);
         return ResponseEntity.ok(memberSignUpReqDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(MemberSignInReqDto memberSignInReqDto){
        JwtTokenResDto jwtToken=memberService.login(memberSignInReqDto);
        return ResponseEntity.ok(jwtToken);
    }
}
