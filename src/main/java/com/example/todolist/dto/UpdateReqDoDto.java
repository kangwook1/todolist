package com.example.todolist.dto;

import com.example.todolist.domain.Do;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UpdateReqDoDto {
    private Long id;
    private boolean status;

    public Do toEntity(){
        return Do.builder()
                .id(id)
                .status(status)
                .build();
    }
}
