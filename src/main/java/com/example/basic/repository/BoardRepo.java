package com.example.basic.repository;

import com.example.basic.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<BoardEntity, Long> {
}