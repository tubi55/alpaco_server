package com.example.basic.repository;

import com.example.basic.entity.JoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepo extends JpaRepository<JoinEntity, Long> {
    JoinEntity findByUnameAndEmail(String uname, String email);
}
