package com.example.todolist.service;


import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import com.example.todolist.dto.request.AddDoReqDto;
import com.example.todolist.dto.response.ReadDoResDto;
import com.example.todolist.repository.DoRepository;
import com.example.todolist.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DoService {
    private final DoRepository doRepository;
    private final MemberRepository memberRepository;

    public List<ReadDoResDto> getTodoList(){
        List<Do> all=doRepository.findAll();

        List<ReadDoResDto> todoList=new ArrayList<>();

        for(Do todo : all){
            ReadDoResDto dodto=new ReadDoResDto();
            todoList.add(dodto.toDto(todo));
        }
        return todoList;
    }
    public void saveDo(String loginId, AddDoReqDto dodto){
        Member member=memberRepository.findByLoginId(loginId).get();
        doRepository.save(dodto.toEntity(member));
    }

    @Transactional
    public void updateDoById(Long id){
        Do todo=doRepository.findById(id).get();
        todo.changeStatus(true);
    }
    public void deleteDoById(Long id){
        doRepository.deleteById(id);
    }

}
