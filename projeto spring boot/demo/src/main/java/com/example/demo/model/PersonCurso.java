package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonCursoId.class)
public class PersonCurso {

    @Id
    @Column(name = "id_person", nullable = false)
    private Long idPerson;

    @Id
    @Column(name = "id_curso", nullable = false)
    private Long idCurso;

    @Column(nullable = true, length = 45)
    private String nivelamento;

    @Column(nullable = false)
    private Boolean concluido;

    @Column(nullable = false)
    private Boolean habilitado;

    // Getters e Setters
    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getNivelamento() {
        return nivelamento;
    }

    public void setNivelamento(String nivelamento) {
        this.nivelamento = nivelamento;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
}
