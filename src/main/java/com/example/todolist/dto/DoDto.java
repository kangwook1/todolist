package com.example.todolist.dto;

import com.example.todolist.domain.Do;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoDto {
    private Long id;
    @NotBlank
    private String content;
    private boolean status;

    @Builder
    public DoDto(String content, boolean status) {
        this.content = content;
        this.status = status;
    }

    public Do toEntity(){
        return Do.builder()
                .content(content)
                .status(status)
                .build();
    }

    public DoDto toDto(Do todo){
        return DoDto.builder()
                .content(todo.getContent())
                .status(todo.getStatus())
                .build();
    }
}
