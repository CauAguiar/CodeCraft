package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonAtividadeId.class)
public class PersonAtividade {

    @Id
    private String personId;

    @Id
    private Long atividadeId;

    private Boolean completou;
    private Boolean confirmou;

    // Getters e Setters
    public String getPersonName() {
        return personId;
    }

    public void setPersonName(String personId) {
        this.personId = personId;
    }

    public Long getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(Long atividadeId) {
        this.atividadeId = atividadeId;
    }

    public Boolean getCompletou() {
        return completou;
    }

    public void setCompletou(Boolean completou) {
        this.completou = completou;
    }

    public Boolean getConfirmou() {
        return confirmou;
    }

    public void setConfirmou(Boolean confirmou) {
        this.confirmou = confirmou;
    }
}

