package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.ExercicioAberto;

public interface ExercicioAbertoRepository extends JpaRepository<ExercicioAberto, Long> {
    @Query(value = """
        SELECT e.* 
        FROM exercicio_aberto e 
        JOIN atividade a ON a.atividade_especifica_id = e.id 
        WHERE a.modulo_id = :moduloId
        """, nativeQuery = true)
    List<ExercicioAberto> findByModuloId(@Param("moduloId") Long moduloId);
}
