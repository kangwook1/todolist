package com.example.todolist.dto.request;

import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    직렬화,역직렬화 시 getter,setter을 통해 진행한다.
    @RequestBody를 통해 DtO로 바인딩을 해주는 역할을 objectMapper가 해준다.
    이때, JSON으로부터 받아온 데이터를 가지고 DTO로 변환할 때 DTO의 기본 생성자를 이용하여 껍데기를 먼저 만드는 것이다.(그래서 기본생성자 필요)
    마지막으로 생성된 필드에 값을 넣어주기만 하면되는데,Reflection이라는 api를 이용해 값을 넣어주는데 setter가 따로 필요 없다.
    따라서 getter와 기본생성자만 있으면 된다.(getter와 setter 둘중 하나만 있으면 된다.)
*/
@Getter
@NoArgsConstructor
public class AddDoReqDto {
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
