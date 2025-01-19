package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    // Adicione métodos personalizados, se necessário
    @Query(value = """
            SELECT *
            FROM atividade
            WHERE modulo_id = :moduloId
            """, nativeQuery = true)
    List<Atividade> findAtividadesByModuloId(@Param("moduloId") Long moduloId);
}
