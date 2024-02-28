package com.example.todolist.repository;

import com.example.todolist.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, QuerydslPredicateExecutor<Member> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsMemberByLoginId(String loginId);
}
