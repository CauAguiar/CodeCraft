package com.example.demo.model;

import java.util.concurrent.atomic.LongAdder;

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

    @Column(nullable = false)
    private String titulo; // Título do quiz (ex.: "Quiz sobre Java Básico")

    @Column(nullable = false)
    private String alternativaA; // Alternativa A

    @Column(nullable = false)
    private String alternativaB; // Alternativa B

    @Column(nullable = false)
    private String alternativaC; // Alternativa C

    @Column(nullable = false)
    private String alternativaD; // Alternativa D

    @Column(nullable = false)
    private Integer respostaCerta; // Resposta correta (ex.: "0", "1", "2", ou "3")

    @Column(nullable = false)
    private String enunciado; // Texto da pergunta (ex.: "Qual é a linguagem de programação mais usada?")

    @Column(nullable = true)
    private Integer respostaSelecionada;

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

    public Integer getRespostaCerta() {
        return respostaCerta;
    }

    public void setRespostaCerta(Integer respostaCerta) {
        this.respostaCerta = respostaCerta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getRespostaSelecionada() {
        return respostaSelecionada;
    }

    public void setRespostaSelecionada(Integer respostaSelecionada) {
        this.respostaSelecionada = respostaSelecionada;
    }
}
