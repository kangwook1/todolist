package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.response.JwtTokenResDto;
import com.example.todolist.dto.request.MemberSignInReqDto;
import com.example.todolist.dto.request.MemberSignUpReqDto;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long join(MemberSignUpReqDto reqDto){
        if(memberRepository.findByLoginId(reqDto.getLoginId()).isPresent()){
            throw new IllegalArgumentException("이미 가입된 유저입니다.");
        }
        Member member=reqDto.toEntity();
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();
        memberRepository.save(member);
        return member.getId();
    }

    public JwtTokenResDto login(MemberSignInReqDto memberSignInReqDto){
        Optional<Member> result= memberRepository.findByLoginId(memberSignInReqDto.getLoginId());
        if(result.isEmpty())
            throw new IllegalArgumentException("가입되지 않은 회원입니다.");
        Member member=result.get();
        if(!passwordEncoder.matches(memberSignInReqDto.toEntity().getPassword(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        JwtTokenResDto token=JwtTokenResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(member.getLoginId(),member.getRole().name()))
                .refreshToken(jwtTokenProvider.createRefreshToken(member.getLoginId()))
                .build();
        return token;
    }


}
