package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonProjetoId implements Serializable {

    private String personId;
    private Long projetoId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonProjetoId that = (PersonProjetoId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(projetoId, that.projetoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, projetoId);
    }
}
