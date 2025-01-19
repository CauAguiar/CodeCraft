package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonExercicioAbertoId implements Serializable {

    private String personId;
    private Long exercicioAbertoId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonExercicioAbertoId that = (PersonExercicioAbertoId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(exercicioAbertoId, that.exercicioAbertoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, exercicioAbertoId);
    }
}
