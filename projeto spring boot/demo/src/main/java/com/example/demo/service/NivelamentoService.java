package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Atividade;
import com.example.demo.model.Nivelamento;
import com.example.demo.model.Quiz;
import com.example.demo.repository.AtividadeRepository;
import com.example.demo.repository.NivelamentoRepository;
import com.example.demo.repository.QuizRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NivelamentoService {

    @Autowired
    private NivelamentoRepository nivelamentoRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private QuizRepository quizRepository;

    // Criar ou atualizar nivelamento
    public Nivelamento salvarNivelamento(Long curso, int nivelAluno, boolean nivelamentoConcluido, List<Long> atividadeIds, List<Long> quizIds) {
        Nivelamento nivelamento = new Nivelamento();
        nivelamento.setCurso(curso);
        nivelamento.setNivelAluno(nivelAluno);
        nivelamento.setNivelamentoConcluido(nivelamentoConcluido);

        // Buscar Atividades pelo ID
        List<Atividade> atividades = atividadeRepository.findAllById(atividadeIds);
        nivelamento.setAtividades(atividades);

        // Buscar Quizzes pelo ID
        List<Quiz> quizzes = quizRepository.findAllById(quizIds);
        nivelamento.setQuizzes(quizzes);

        // Salvar no banco de dados
        return nivelamentoRepository.save(nivelamento);
    }
}


