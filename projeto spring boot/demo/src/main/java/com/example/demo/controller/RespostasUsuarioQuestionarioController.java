package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RespostasUsuarioQuestionario;
import com.example.demo.service.RespostasUsuarioQuestionarioService;

@RestController
@RequestMapping("/api/respostas-usuario-questionario")
public class RespostasUsuarioQuestionarioController {

    @Autowired
    private RespostasUsuarioQuestionarioService respostasUsuarioQuestionarioService;

    @GetMapping("/getByPersonId")
    public ResponseEntity<List<RespostasUsuarioQuestionario>> getRespostasByPersonId(@RequestParam(name = "personId") Long personId) {
        System.out.println("Receive person id: " + personId);
        List<RespostasUsuarioQuestionario> respostas = respostasUsuarioQuestionarioService.getRespostasByPersonId(personId);
        return ResponseEntity.ok(respostas);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addResposta(
            @RequestParam Long personId,
            @RequestParam Long perguntaId,
            @RequestParam Long respostaId) {
        respostasUsuarioQuestionarioService.insertResposta(personId, perguntaId, respostaId);
        return ResponseEntity.ok().build();
    }

}
