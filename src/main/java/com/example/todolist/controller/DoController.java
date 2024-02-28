package com.example.todolist.controller;

import com.example.todolist.dto.request.AddDoReqDto;
import com.example.todolist.dto.response.ReadDoResDto;
import com.example.todolist.service.DoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DoController {
    private final DoService doService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ReadDoResDto>> todoList(){
        return ResponseEntity.ok(doService.getTodoList());
    }

    @PostMapping(value = "/")
    //session에 있는 권한을 받아온다.
    public ResponseEntity<AddDoReqDto> saveDo(Authentication authentication, @RequestBody AddDoReqDto dodto){
        String loginId=authentication.getPrincipal().toString();
        doService.saveDo(loginId,dodto);
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
        return ResponseEntity.ok(id);
    }

}
