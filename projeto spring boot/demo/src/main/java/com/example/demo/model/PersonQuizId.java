package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonQuizId implements Serializable {

    private String personId;
    private Long quizId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonQuizId that = (PersonQuizId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(quizId, that.quizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, quizId);
    }
}
