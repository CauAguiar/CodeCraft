package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonCursoId implements Serializable {

    private Long idPerson;
    private Long idCurso;

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

    // hashCode e equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCursoId that = (PersonCursoId) o;
        return Objects.equals(idPerson, that.idPerson) && Objects.equals(idCurso, that.idCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerson, idCurso);
    }
}
