package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(name = "alternativaA", nullable = false, length = 255)
    private String alternativaA;

    @Column(name = "alternativaB", nullable = false, length = 255)
    private String alternativaB;

    @Column(name = "alternativaC", nullable = false, length = 255)
    private String alternativaC;

    @Column(name = "alternativaD", nullable = false, length = 255)
    private String alternativaD;

    @Column(nullable = false, length = 255)
    private String enunciado;

    @Column(name = "resposta_correta", nullable = false, columnDefinition = "CHAR(1)")
    private String resposta_correta;    

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespostaCorreta() {
        return resposta_correta;
    }

    public void setRespostaCorreta(String resposta_correta) {
        this.resposta_correta = resposta_correta;
    }
}
