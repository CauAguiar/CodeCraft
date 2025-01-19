package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonAtividadeId implements Serializable {

    private String personId;
    private Long atividadeId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonAtividadeId that = (PersonAtividadeId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(atividadeId, that.atividadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, atividadeId);
    }
}
