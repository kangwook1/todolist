package com.example.todolist.repository;

import com.example.todolist.domain.EmailKey;
import org.springframework.data.repository.CrudRepository;

public interface EmailKeyRepository extends CrudRepository<EmailKey,String> {
}
