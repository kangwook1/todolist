package com.example.todolist.dto.response;

import com.example.todolist.domain.Do;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ReadDoResDto {
    private Long id;
    @NotBlank
    private String content;
    private boolean status;
    private Long memberId;

    @Builder
    public ReadDoResDto(Long id, String content, boolean status, Long memberId) {
        this.id=id;
        this.content = content;
        this.status = status;
        this.memberId=memberId;
    }

    public ReadDoResDto toDto(Do todo){
        return ReadDoResDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .status(todo.getStatus())
                .memberId(todo.getMember().getId())
                .build();
    }
}
