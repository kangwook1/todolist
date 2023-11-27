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
public class AddReqDoDto {
    @NotBlank
    private String content;
    private boolean status;

    public Do toEntity(){
        return Do.builder()
                .content(content)
                .status(status)
                .build();
    }
}
