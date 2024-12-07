package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Nivelamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cursoId; // ID do curso

    private int nivelAluno; // Nível do aluno (1 a 5)

    private boolean nivelamentoConcluido; // Se o nivelamento foi concluído

    @OneToMany(mappedBy = "nivelamento")
    private List<Atividade> atividades;

    @OneToMany(mappedBy = "nivelamento")
    private List<Quiz> quizzes;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
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

