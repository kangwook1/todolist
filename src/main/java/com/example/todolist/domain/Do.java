package com.example.todolist.domain;

import com.example.todolist.dto.DoDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Do extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean status;

    @Builder
    public Do(Long id,String content, Boolean status) {
        this.id=id;
        this.content = content;
        this.status = status;
    }

    public void changeContent(String content){
        this.content=content;
    }

    public void changeStatus(boolean status){
        this.status=status;
    }

}
