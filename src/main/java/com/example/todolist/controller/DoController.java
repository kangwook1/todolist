package com.example.todolist.controller;

import com.example.todolist.dto.request.AddDoReqDto;
import com.example.todolist.dto.response.ReadDoResDto;
import com.example.todolist.service.DoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DoController {
    private final DoService doService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ReadDoResDto>> todoList(Principal principal){
        String loginId=principal.getName();//toString은 보통 디버깅 목적,getName이 아이디반환
        return ResponseEntity.ok(doService.getTodoList(loginId));
    }

    @PostMapping(value = "/")
    //session에 있는 권한을 받아온다.//@valid 빠짐
    public ResponseEntity<AddDoReqDto> saveDo(Authentication authentication, @RequestBody @Valid AddDoReqDto dodto){
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
