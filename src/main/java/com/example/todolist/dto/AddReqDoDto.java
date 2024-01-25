package com.example.todolist.dto;

import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddReqDoDto {
    @NotBlank
    private String content;
    private boolean status;

    public Do toEntity(Member member){
        return Do.builder()
                .content(content)
                .status(status)
                .member(member)
                .build();
    }
}
