package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query(value = """
            SELECT p.*
            FROM projeto p
            JOIN atividade a ON a.atividade_especifica_id = p.id
            WHERE a.modulo_id = :moduloId
            """, nativeQuery = true)
    List<Projeto> findByModuloId(@Param("moduloId") Long moduloId);
}