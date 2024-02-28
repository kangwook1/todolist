package com.example.todolist.security;

import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //password는 authenticationProvider에서 검증한다. 보통 스프링에서 자동구현해준다.
    @Override
    public UserDetails loadUserByUsername(String loginId){
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_EXIST_USER));
    }
}
