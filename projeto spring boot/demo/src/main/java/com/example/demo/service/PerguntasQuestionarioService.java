package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.PerguntasQuestionario;
import com.example.demo.repository.PerguntasQuestionarioRepository;

@Service
public class PerguntasQuestionarioService {

    @Autowired
    private PerguntasQuestionarioRepository perguntasQuestionarioRepository;

    public List<PerguntasQuestionario> getPerguntasByCursoId(Long cursoId) {
        if (cursoId == null) {
            throw new IllegalArgumentException("O id do curso não pode ser nulo");
        }
        return perguntasQuestionarioRepository.findByIdCurso(cursoId);
    }

}
