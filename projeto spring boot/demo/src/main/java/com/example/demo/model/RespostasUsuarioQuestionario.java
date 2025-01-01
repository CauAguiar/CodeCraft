package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RespostasUsuarioQuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta_usuario")
    private Long idRespostaUsuario;

    @Column(name = "id_person", nullable = false)
    private Long idPerson;

    @Column(name = "id_pergunta", nullable = false)
    private Long idPergunta;

    @Column(name = "id_resposta", nullable = false)
    private Long idResposta;

    @Column(name = "data_resposta", nullable = false)
    private LocalDateTime dataResposta;

    // Getters e Setters
    public Long getIdRespostaUsuario() {
        return idRespostaUsuario;
    }

    public void setIdRespostaUsuario(Long idRespostaUsuario) {
        this.idRespostaUsuario = idRespostaUsuario;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Long getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Long idResposta) {
        this.idResposta = idResposta;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }
}
