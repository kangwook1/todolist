package com.example.todolist.controller;

import com.example.todolist.service.DoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoController {
    private final DoService doService;

    @Autowired
    public DoController(DoService doService) {
        this.doService = doService;
    }

    @GetMapping(value = "/")
    public String index(Model model){

    }

}
