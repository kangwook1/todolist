package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberDto;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void signup(MemberDto memberDto){
       memberRepository.save(memberDto.signupToEntity());
    }
}
