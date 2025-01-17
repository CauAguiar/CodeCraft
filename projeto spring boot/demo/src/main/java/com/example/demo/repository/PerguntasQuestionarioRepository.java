package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PerguntasQuestionario;

@Repository
public interface PerguntasQuestionarioRepository extends JpaRepository<PerguntasQuestionario, Long> {
    @Query(value = "SELECT * FROM perguntas_questionario WHERE id_curso = :cursoId", nativeQuery = true)
    List<PerguntasQuestionario> findByIdCurso(@Param("cursoId") Long cursoId);

}
