package com.example.todolist.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
//@MappedSuperclass는 실제 테이블과 매핑되지 않는다. 단순히 매핑 정보를 상속할 목적으로만 사용된다.
@MappedSuperclass
/*
    엔티티를 DB에 적용하기 전,후에 커스텀 콜백을 요청할 수 있는 어노테이션이다.
    인자로 커스텀 콜백을 요청할 클래스를 지정해주면 되는데,
    Auditing 을 수행할 때는 JPA 에서 제공하는 AuditingEntityListener.class 를 인자로 넘기면 된다.
*/
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "registration_date",nullable = false, updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modification_date",nullable = false)
    private LocalDateTime modDate;
}
