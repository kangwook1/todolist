package com.example.todolist.service;


import com.example.todolist.domain.Do;
import com.example.todolist.dto.AddReqDoDto;
import com.example.todolist.dto.ReadResDoDto;
import com.example.todolist.dto.UpdateReqDoDto;
import com.example.todolist.repository.DoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoService {
    private final DoRepository doRepository;

    public List<ReadResDoDto> getTodoList(){
        List<Do> all=doRepository.findAll();

        List<ReadResDoDto> todoList=new ArrayList<>();

        for(Do todo : all){
            ReadResDoDto dodto=new ReadResDoDto();
            todoList.add(dodto.toDto(todo));
        }
        return todoList;
    }
    public void saveDo(AddReqDoDto dodto){
        doRepository.save(dodto.toEntity());
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
