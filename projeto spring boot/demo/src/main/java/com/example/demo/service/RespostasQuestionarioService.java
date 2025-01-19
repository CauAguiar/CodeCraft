package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RespostasQuestionario;
import com.example.demo.repository.RespostasQuestionarioRepository;

@Service
public class RespostasQuestionarioService {

    @Autowired
    private RespostasQuestionarioRepository respostasQuestionarioRepository;

    public List<RespostasQuestionario> getRespostasByCursoId(Long cursoId) {
        if (cursoId == null) {
            throw new IllegalArgumentException("O id do curso não pode ser nulo");
        }
        return respostasQuestionarioRepository.findByIdPergunta(cursoId);
    }
}
