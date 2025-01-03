package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query("SELECT u FROM Unidade u WHERE u.idCurso IN (SELECT c.id FROM Curso c WHERE LOWER(c.nome) = LOWER(:languageName))")
    List<Unidade> findByCursoNome(@Param("languageName") String languageName);
}


