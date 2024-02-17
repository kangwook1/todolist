package com.example.todolist.repository;

import com.example.todolist.domain.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken,String> {
}
