package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RespostasQuestionario;
import com.example.demo.service.RespostasQuestionarioService;

@RestController
@RequestMapping("/api/respostas-questionario")
public class RespostasQuestionarioController {

    @Autowired
    private RespostasQuestionarioService respostasQuestionarioService;

    @GetMapping("/getByPerguntaId")
    public ResponseEntity<List<RespostasQuestionario>> getRespostasByPerguntaId(@RequestParam(name = "perguntaId") Long perguntaId) {
        System.out.println("Receive pergunta id: " + perguntaId);
        List<RespostasQuestionario> respostas = respostasQuestionarioService.getRespostasByCursoId(perguntaId);
        return ResponseEntity.ok(respostas);
    }

}
