package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PerguntasQuestionario;

@Repository
public interface PerguntasQuestionarioRepository extends JpaRepository<PerguntasQuestionario, Long> {
    @Query(value = "SELECT * FROM perguntas_questionario WHERE id_curso = :idCurso", nativeQuery = true)
    List<PerguntasQuestionario> findByIdCurso(Long idCurso);

}
