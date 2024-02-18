package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.response.JwtTokenResDto;
import com.example.todolist.dto.request.MemberSignInReqDto;
import com.example.todolist.dto.request.MemberSignUpReqDto;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long join(MemberSignUpReqDto reqDto){
        if(memberRepository.findByLoginId(reqDto.getLoginId()).isPresent())
               throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        Member member=reqDto.toEntity();
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();
        memberRepository.save(member);
        return member.getId();
    }

    public JwtTokenResDto login(MemberSignInReqDto memberSignInReqDto){
        Member member= memberRepository.findByLoginId(memberSignInReqDto.getLoginId())
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_LOGIN_ID));
        if(!passwordEncoder.matches(memberSignInReqDto.getPassword(),member.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        JwtTokenResDto token=JwtTokenResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(member.getLoginId(),member.getRole().name()))
                .refreshToken(jwtTokenProvider.createRefreshToken(member.getLoginId()))
                .build();
        return token;
    }


}
