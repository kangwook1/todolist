package com.example.todolist.controller;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/signup")
    public String signup(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "signup";
    }

    @PostMapping(value = "/signupMember")
    public String signupMember(MemberDto memberDto){
        memberService.signup(memberDto);
        return "redirect:/";
    }
}
