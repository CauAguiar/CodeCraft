package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonVideoId.class)
public class PersonVideo {

    @Id
    private String personId;

    @Id
    private Long videoId;

    private Boolean assistido;
    private Boolean confirmou;

    // Getters e Setters
    public String getPersonName() {
        return personId;
    }

    public void setPersonName(String personId) {
        this.personId = personId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Boolean getAssistido() {
        return assistido;
    }

    public void setAssistido(Boolean assistido) {
        this.assistido = assistido;
    }

    public Boolean getConfirmou() {
        return confirmou;
    }

    public void setConfirmou(Boolean confirmou) {
        this.confirmou = confirmou;
    }
}

