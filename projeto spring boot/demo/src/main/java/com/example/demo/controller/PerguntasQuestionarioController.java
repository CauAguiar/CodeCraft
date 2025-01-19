package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PerguntasQuestionario;
import com.example.demo.service.PerguntasQuestionarioService;

@RestController
@RequestMapping("/api/perguntas-questionario")
public class PerguntasQuestionarioController {

    @Autowired
    private PerguntasQuestionarioService perguntasQuestionarioService;

    @GetMapping("/getByCursoId")
    public ResponseEntity<List<PerguntasQuestionario>> getPerguntasByCursoId(
            @RequestParam(name = "cursoId") Long cursoId) {
        System.out.println("Receive curso id: " + cursoId);
        List<PerguntasQuestionario> perguntas = perguntasQuestionarioService.getPerguntasByCursoId(cursoId);
        return ResponseEntity.ok(perguntas);
    }
}
