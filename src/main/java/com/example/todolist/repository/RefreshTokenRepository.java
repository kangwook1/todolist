package com.example.todolist.repository;

import com.example.todolist.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

//Redis Rettuce를 사용하기 위해서 CrudRepository를 상속한다.
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}
