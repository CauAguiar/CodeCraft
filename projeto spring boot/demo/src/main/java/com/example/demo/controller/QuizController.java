package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Quiz;
import com.example.demo.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/getByModuloId")
    public ResponseEntity<List<Quiz>> getQuizzes(@RequestParam("moduloId") Long moduloId) {
        List<Quiz> quizzes = quizService.getQuizzesByModuloId(moduloId);
        return ResponseEntity.ok(quizzes);
    }
}
