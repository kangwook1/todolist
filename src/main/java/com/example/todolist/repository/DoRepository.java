package com.example.todolist.repository;

import com.example.todolist.domain.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DoRepository extends JpaRepository<Do,Long>, QuerydslPredicateExecutor<Do> {
}
