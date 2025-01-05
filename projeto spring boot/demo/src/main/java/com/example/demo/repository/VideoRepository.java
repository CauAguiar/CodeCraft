package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = """
        SELECT v.* 
        FROM video v 
        JOIN atividade a ON a.atividade_especifica_id = v.id 
        WHERE a.modulo_id = :moduloId
        """, nativeQuery = true)
    List<Video> findByModuloId(@Param("moduloId") Long moduloId);
}
