package com.example.todolist.repository;

import com.example.todolist.domain.Do;
import com.example.todolist.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface DoRepository extends JpaRepository<Do,Long>, QuerydslPredicateExecutor<Do> {

    Optional<List<Do>> findAllByMember(Member member);
}
