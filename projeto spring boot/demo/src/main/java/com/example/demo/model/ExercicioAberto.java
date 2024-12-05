package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExercicioAberto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String enunciado; // Enunciado do exercício (ex.: "Escreva um algoritmo que...")

    @Column(nullable = true)
    private String respostaEsperada; // Resposta esperada (opcional)

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespostaEsperada() {
        return respostaEsperada;
    }

    public void setRespostaEsperada(String respostaEsperada) {
        this.respostaEsperada = respostaEsperada;
    }
}
