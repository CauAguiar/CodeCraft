package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonLicaoId implements Serializable {

    private Long personId;
    private Long licaoId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonLicaoId that = (PersonLicaoId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(licaoId, that.licaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, licaoId);
    }
}
