package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getQuizzesByModuloId(Long moduloId) {
        return quizRepository.findByModuloId(moduloId);
    }
}
