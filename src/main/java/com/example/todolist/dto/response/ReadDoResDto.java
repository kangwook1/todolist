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
    private String content;
    private boolean status;

    @Builder
    public ReadDoResDto(Long id, String content, boolean status) {
        this.id=id;
        this.content = content;
        this.status = status;
    }

}
