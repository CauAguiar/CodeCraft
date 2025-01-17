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

/* @Repository
public interface PerguntasQuestionarioRepository extends JpaRepository<PerguntasQuestionario, Long> {
    //@Query(value = "SELECT DISTINCT p.*, r.* FROM perguntas_questionario p LEFT JOIN respostas_questionario r ON p.id_pergunta = r.id_pergunta WHERE p.id_curso = :cursoId", nativeQuery = true)
    //@Query(value = "SELECT p.id_pergunta AS p_id_pergunta, p.*, r.id_pergunta AS r_id_pergunta, r.* FROM perguntas_questionario p LEFT JOIN respostas_questionario r ON p.id_pergunta = r.id_pergunta WHERE p.id_curso = :cursoId", nativeQuery = true)
    @Query(value = """
        SELECT 
            p.id_pergunta AS p_id_pergunta, 
            p.pergunta AS p_pergunta, 
            p.id_curso AS p_id_curso,
            r.id_pergunta AS r_id_pergunta, 
            r.resposta AS r_resposta
        FROM perguntas_questionario p 
        LEFT JOIN respostas_questionario r 
        ON p.id_pergunta = r.id_pergunta 
        WHERE p.id_curso = :cursoId
        """, nativeQuery = true)
     */
    /* List<PerguntasQuestionario> findByIdCurso(@Param("cursoId") Long cursoId); */
//}
