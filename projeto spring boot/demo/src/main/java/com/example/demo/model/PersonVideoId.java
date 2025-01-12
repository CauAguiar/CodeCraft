package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonVideoId implements Serializable {

    private String personId;
    private Long videoId;

    // Getters, Setters, equals e hashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonVideoId that = (PersonVideoId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, videoId);
    }
}

