package com.example.todolist.controller;


import com.example.todolist.dto.request.CheckEmailKeyReqDto;
import com.example.todolist.dto.request.FindPasswordReqDto;
import com.example.todolist.dto.request.SendEmailReqDto;
import com.example.todolist.service.MailService;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping(value = "/id")
    public ResponseEntity<?> findId(@RequestBody SendEmailReqDto sendEmailReqDto){
        //회원가입할 때 이메일 중복검사를 하기때문에 따로 할 필요 없다.
        return ResponseEntity.ok(mailService.findId(sendEmailReqDto));

    }

    @PatchMapping(value = "/password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordReqDto findPasswordReqDto){
        return ResponseEntity.ok(mailService.findPassword(findPasswordReqDto));

    }
    @PostMapping(value = "/key")
    public ResponseEntity<?> sendEmailKey(@RequestBody SendEmailReqDto sendEmailReqDto){
        return ResponseEntity.ok(mailService.sendEmailKey(sendEmailReqDto));

    }

    @PostMapping(value = "/check")
    public ResponseEntity<?> checkEmailKey(@RequestBody CheckEmailKeyReqDto checkEmailKeyReqDto){
        return ResponseEntity.ok(mailService.checkEmailKeyEqual(checkEmailKeyReqDto));

    }

}
