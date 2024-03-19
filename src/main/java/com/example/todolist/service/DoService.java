package com.example.todolist.service;


import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import com.example.todolist.dto.request.AddDoReqDto;
import com.example.todolist.dto.response.ReadDoResDto;
import com.example.todolist.repository.DoRepository;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DoService {
    private final DoRepository doRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)//조회용 메소드는 변경감지를 제외해 데이터변경을 최소화한다.
    public List<ReadDoResDto> getTodoList(String loginId){
        Member member=memberRepository.findByLoginId(loginId).get();
        List<Do> all=doRepository.findAllByMember(member).get();
        List<ReadDoResDto> todoList=new ArrayList<>();

        for(Do todo : all){
            todoList.add(ReadDoResDto.builder()
                            .id(todo.getId())
                            .content(todo.getContent())
                            .status(todo.getStatus())
                    .build());
        }
        return todoList;
    }
    public void saveDo(String loginId, AddDoReqDto dodto){
        Member member=memberRepository.findByLoginId(loginId).get();
        //널체크하지 않은이유=세션에 로그인 아이디가 존재하기 때문. 또한 요청 전 jwt필터에서 예외처리를 통해 인증이 되지않으면 예외처리가 뜬다.
        doRepository.save(dodto.toEntity(member));
    }

    public void updateDoById(Long id){
        Do todo=doRepository.findById(id).get();//
        todo.changeStatus(true);
    }
    public void deleteDoById(Long id){
        doRepository.deleteById(id);
    }

}
