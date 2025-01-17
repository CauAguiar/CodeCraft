package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RespostasQuestionario;

@Repository
public interface RespostasQuestionarioRepository extends JpaRepository<RespostasQuestionario, Long> {
    @Query(value = "SELECT * FROM respostas_questionario WHERE id_pergunta = :perguntaId", nativeQuery = true)
    List<RespostasQuestionario> findByIdPergunta(@Param("perguntaId") Long perguntaId);
}
