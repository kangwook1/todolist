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
public class ReadResDoDto {
    private Long id;
    @NotBlank
    private String content;
    private boolean status;
    private Long memberId;

    @Builder
    public ReadResDoDto(Long id, String content, boolean status,Long memberId) {
        this.id=id;
        this.content = content;
        this.status = status;
        this.memberId=memberId;
    }

    public ReadResDoDto toDto(Do todo){
        return ReadResDoDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .status(todo.getStatus())
                .memberId(todo.getMember().getId())
                .build();
    }
}
