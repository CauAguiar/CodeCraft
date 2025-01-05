package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = """
        SELECT q.* 
        FROM quiz q 
        JOIN atividade a ON a.atividade_especifica_id = q.id 
        WHERE a.modulo_id = :moduloId
        """, nativeQuery = true)
    List<Quiz> findByModuloId(@Param("moduloId") Long moduloId);
}
