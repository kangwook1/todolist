package com.example.todolist.service;


import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import com.example.todolist.dto.AddReqDoDto;
import com.example.todolist.dto.ReadResDoDto;
import com.example.todolist.dto.UpdateReqDoDto;
import com.example.todolist.repository.DoRepository;
import com.example.todolist.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DoService {
    private final DoRepository doRepository;
    private final MemberRepository memberRepository;

    public List<ReadResDoDto> getTodoList(){
        List<Do> all=doRepository.findAll();

        List<ReadResDoDto> todoList=new ArrayList<>();

        for(Do todo : all){
            ReadResDoDto dodto=new ReadResDoDto();
            todoList.add(dodto.toDto(todo));
        }
        return todoList;
    }
    public void saveDo(String loginId,AddReqDoDto dodto){
        log.info("success");
        Member member=memberRepository.findByLoginId(loginId).get();
        log.info("sagasd");
        doRepository.save(dodto.toEntity(member));
    }

    @Transactional
    public void updateDoById(Long id){
        Do ett=doRepository.findById(id).get();
        ett.changeStatus(true);
    }
    public void deleteDoById(Long id){
        doRepository.deleteById(id);
    }

}
