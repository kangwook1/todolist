package com.example.todolist.service;


import com.example.todolist.domain.Do;
import com.example.todolist.dto.DoDto;
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

    public List<DoDto> getTodoList(){
        List<Do> all=doRepository.findAll();

        List<DoDto> todoList=new ArrayList<>();

        for(Do todo : all){
            DoDto dodto=new DoDto();
            todoList.add(dodto.toDto(todo));
        }
        return todoList;
    }
    public void saveDo(DoDto dodto){
        doRepository.save(dodto.toEntity());
    }

    @Transactional
    public void updateDo(DoDto doDto){
        Do ett=doRepository.findById(doDto.toEntity().getId()).get();
        ett.changeStatus(true);
    }
    public void deleteDo(DoDto doDto){
        doRepository.delete(doDto.toEntity());
    }

}
