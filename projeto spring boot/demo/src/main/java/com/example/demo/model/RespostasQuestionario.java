package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RespostasQuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Long idResposta;

    @Column(name = "id_pergunta", nullable = false)
    private Long idPergunta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resposta;

    @Column(nullable = true)
    private Integer peso;

    // Getters e Setters
    public Long getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Long idResposta) {
        this.idResposta = idResposta;
    }

    public Long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
}
