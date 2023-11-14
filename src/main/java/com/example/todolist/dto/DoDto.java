package com.example.todolist.dto;

import com.example.todolist.domain.Do;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoDto {
    private Long id;
    @NotBlank
    private String content;
    private boolean status;

    @Builder
    public DoDto(Long id,String content, boolean status) {
        this.id=id;
        this.content = content;
        this.status = status;
    }

    public Do toEntity(){
        return Do.builder()
                .id(id)
                .content(content)
                .status(status)
                .build();
    }

    public DoDto toDto(Do todo){
        return DoDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .status(todo.getStatus())
                .build();
    }
}
