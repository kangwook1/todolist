package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.request.SignUpMemberReqDto;
import com.example.todolist.dto.response.LoginJwtTokenResDto;
import com.example.todolist.dto.request.SignInMemberReqDto;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long join(SignUpMemberReqDto reqDto){
        //로그인 아이디 중복검사
        if(checkLoginIdDuplicated(reqDto.getLoginId()))
               throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        //이메일 중복검사
        if(checkEmailDuplicated(reqDto.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        Member member=reqDto.toEntity();
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public LoginJwtTokenResDto login(SignInMemberReqDto signInMemberReqDto){
        //로그인폼을 이용해 필터에서 토큰을 인증객체를 만드는게 아니고,
        //서비스에서 토큰을 만들어 반환하기때문에 그전에 유효성 검사를 하는 것이다.
        Member member= memberRepository.findByLoginId(signInMemberReqDto.getLoginId())
                .orElseThrow(()->new CustomException(ErrorCode.INVALID_LOGIN_ID));
        if(!passwordEncoder.matches(signInMemberReqDto.getPassword(),member.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        LoginJwtTokenResDto token= LoginJwtTokenResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(member.getLoginId(),member.getRole().name()))
                .refreshToken(jwtTokenProvider.createRefreshToken(member.getLoginId()))
                .build();
        return token;
    }

    public boolean checkLoginIdDuplicated(String loginId){
        return memberRepository.existsMemberByLoginId(loginId);
    }
    public boolean checkEmailDuplicated(String email){
        return memberRepository.existsMemberByLoginId(email);
    }


}
