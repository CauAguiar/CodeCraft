package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.RespostasUsuarioQuestionario;

@Repository
public interface RespostasUsuarioQuestionarioRepository extends JpaRepository<RespostasUsuarioQuestionario, Long> {
        @Query(value = "SELECT * FROM respostas_usuario_questionario WHERE id_person = :personId", nativeQuery = true)
        List<RespostasUsuarioQuestionario> findByIdPerson(@Param("personId") Long personId);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO respostas_usuario_questionario (id_person, id_pergunta, id_resposta, data_resposta) VALUES (:personId, :perguntaId, :respostaId, CURRENT_TIMESTAMP)", nativeQuery = true)
        void insertResposta(@Param("personId") Long personId, @Param("perguntaId") Long perguntaId,
                        @Param("respostaId") Long respostaId);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO person_curso (id_person, id_curso, nivelamento, concluido, habilitado) VALUES (:personId, :cursoId, :nivelamento, :concluido, :habilitado)", nativeQuery = true)
        void insertNivelamento(@Param("personId") Long personId, @Param("cursoId") Long cursoId,
                        @Param("nivelamento") String nivelamento, @Param("concluido") boolean concluido,
                        @Param("habilitado") boolean habilitado);

        @Query(value = "SELECT COUNT(*) > 0 FROM person_curso WHERE id_person = :personId AND id_curso = :cursoId AND nivelamento = :nivelamento AND concluido = :concluido AND habilitado = :habilitado", nativeQuery = true)
        boolean existsByPersonIdAndCursoIdAndNivelamentoAndConcluidoAndHabilitado(@Param("personId") Long personId,
                        @Param("cursoId") Long cursoId, @Param("nivelamento") String nivelamento,
                        @Param("concluido") boolean concluido, @Param("habilitado") boolean habilitado);
}
