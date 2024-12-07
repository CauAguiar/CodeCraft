package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

import java.util.List;

@Entity
public class Nivelamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long curso; // ID do curso

    private int nivelAluno; // Nível do aluno (1 a 5)

    private boolean nivelamentoConcluido; // Se o nivelamento foi concluído

    // Relação com Atividade
    @ManyToMany
    @JoinTable(
            name = "nivelamento_relacao", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "nivelamento_id"),
            inverseJoinColumns = @JoinColumn(name = "atividade_id")
    )
    private List<Atividade> atividades;

    // Relação com Quiz
    @ManyToMany
    @JoinTable(
            name = "nivelamento_relacao", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "nivelamento_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public int getNivelAluno() {
        return nivelAluno;
    }

    public void setNivelAluno(int nivelAluno) {
        this.nivelAluno = nivelAluno;
    }

    public boolean isNivelamentoConcluido() {
        return nivelamentoConcluido;
    }

    public void setNivelamentoConcluido(boolean nivelamentoConcluido) {
        this.nivelamentoConcluido = nivelamentoConcluido;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
