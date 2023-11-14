package com.example.todolist.controller;

import com.example.todolist.dto.DoDto;
import com.example.todolist.service.DoService;
import groovy.util.logging.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Log4j
public class DoController {
    private final DoService doService;

    @Autowired
    public DoController(DoService doService) {
        this.doService = doService;
    }

    @GetMapping(value = "/")
    public String todoList(Model model){
        List<DoDto> todoList=doService.getTodoList();
        model.addAttribute("todoList",todoList);
        model.addAttribute("dodto",new DoDto());
        return "index";
    }

    @PostMapping(value = "/register")
    public String saveDo(DoDto dodto){
        doService.saveDo(dodto);
        return "redirect:/";
    }

    @PostMapping(value = "/update")
    public String updateDo(DoDto dodto){
        doService.updateDo(dodto);
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteDo(DoDto dodto){
        doService.deleteDo(dodto);
        return "redirect:/";
    }

}
