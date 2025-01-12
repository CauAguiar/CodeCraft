package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(PersonLicaoId.class)
@Table(name = "person_licao")
public class PersonLicao {

    @Id
    @Column(name = "person_id")
    private Long personId;

    @Id
    @Column(name = "licao_id")
    private Long licaoId;

    @Column(name = "progresso")
    private String progresso;  // Exemplo: "Em andamento", "Concluído"

    @Column(name = "completou")
    private Boolean completou;

    @Column(name = "confirmou")
    private Boolean confirmou;

    // Getters e Setters
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getLicaoId() {
        return licaoId;
    }

    public void setLicaoId(Long licaoId) {
        this.licaoId = licaoId;
    }

    public String getProgresso() {
        return progresso;
    }

    public void setProgresso(String progresso) {
        this.progresso = progresso;
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

