package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//기본생성자가 필요한 이유는 Reflection api를 이용해 객체를 생성해놓고 동적으로 필드 값 매핑을 한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Do extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "do_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Do(String content, Boolean status, Member member) {
        this.content = content;
        this.status = status;
        this.member=member;
    }

    public void changeStatus(boolean status){
        this.status=status;
    }

}
