package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonExercicioAbertoId.class)
public class PersonExercicioAberto {

    @Id
    private String personId;

    @Id
    private Long exercicioAbertoId;

    private String resposta;
    private Boolean confirmou;
    private Boolean correto;

    // Getters e Setters
    public String getPersonName() {
        return personId;
    }

    public void setPersonName(String personId) {
        this.personId = personId;
    }

    public Long getExercicioAbertoId() {
        return exercicioAbertoId;
    }

    public void setExercicioAbertoId(Long exercicioAbertoId) {
        this.exercicioAbertoId = exercicioAbertoId;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Boolean getConfirmou() {
        return confirmou;
    }

    public void setConfirmou(Boolean confirmou) {
        this.confirmou = confirmou;
    }

    public Boolean getCorreto() {
        return correto;
    }

    public void setCorreto(Boolean correto) {
        this.correto = correto;
    }
}
