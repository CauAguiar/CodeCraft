package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonProjetoId.class)
public class PersonProjeto {

    @Id
    private String personId;

    @Id
    private Long projetoId;

    private Boolean finalizado;
    private Boolean confirmou;

    // Getters e Setters
    public String getPersonName() {
        return personId;
    }

    public void setPersonName(String personId) {
        this.personId = personId;
    }

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Boolean getConfirmou() {
        return confirmou;
    }

    public void setConfirmou(Boolean confirmou) {
        this.confirmou = confirmou;
    }
}
