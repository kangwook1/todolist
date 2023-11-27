package com.example.todolist.controller;

import com.example.todolist.dto.AddReqDoDto;
import com.example.todolist.dto.ReadResDoDto;
import com.example.todolist.dto.UpdateReqDoDto;
import com.example.todolist.service.DoService;
import groovy.util.logging.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j
public class DoController {
    private final DoService doService;

    @Autowired
    public DoController(DoService doService) {
        this.doService = doService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<ReadResDoDto>> todoList(){
        return ResponseEntity.ok(doService.getTodoList());
    }

    @PostMapping(value = "/")
    public ResponseEntity<AddReqDoDto> saveDo(AddReqDoDto dodto){
        doService.saveDo(dodto);
        return ResponseEntity.ok(dodto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateDo(@PathVariable Long id){
        doService.updateDoById(id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDo(@PathVariable Long id){
        doService.deleteDoById(id);
        return ResponseEntity.ok().build();
    }

}
