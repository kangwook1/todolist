package com.example.todolist.service;


import com.example.todolist.dto.DoDto;
import com.example.todolist.repository.DoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoService {
    private final DoRepository doRepository;

    public Long saveDo(DoDto doDto){
        doRepository.save(doDto.toEntity());
        return doDto.toEntity().getId();
    }

    public void completeDo(DoDto doDto){
        doDto.toEntity().changeStatus(true);
    }
    public void deleteDo(DoDto doDto){
        doRepository.delete(doDto.toEntity());
    }

}
