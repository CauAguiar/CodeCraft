package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonQuizId.class)
public class PersonQuiz {

    @Id
    private String personId;

    @Id
    private Long quizId;

    private String respostaSelecionada;
    private Boolean acertou;
    private Boolean confirmou;

    // Getters e Setters
    public String getPersonName() {
        return personId;
    }

    public void setPersonName(String personId) {
        this.personId = personId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getRespostaSelecionada() {
        return respostaSelecionada;
    }

    public void setRespostaSelecionada(String respostaSelecionada) {
        this.respostaSelecionada = respostaSelecionada;
    }

    public Boolean getAcertou() {
        return acertou;
    }

    public void setAcertou(Boolean acertou) {
        this.acertou = acertou;
    }

    public Boolean getConfirmou() {
        return confirmou;
    }

    public void setConfirmou(Boolean confirmou) {
        this.confirmou = confirmou;
    }
}


