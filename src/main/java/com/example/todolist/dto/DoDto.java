package com.example.todolist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoDto {
    private long id;
    private String content;
    private Boolean status;
}
