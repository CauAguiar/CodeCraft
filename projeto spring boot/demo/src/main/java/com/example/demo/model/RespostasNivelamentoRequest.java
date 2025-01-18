package com.example.demo.model;

import java.util.List;

public class RespostasNivelamentoRequest {
    private List<RespostasUsuarioQuestionario> respostas;
    private Long cursoId;

    // Getters and Setters
    public List<RespostasUsuarioQuestionario> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostasUsuarioQuestionario> respostas) {
        this.respostas = respostas;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
