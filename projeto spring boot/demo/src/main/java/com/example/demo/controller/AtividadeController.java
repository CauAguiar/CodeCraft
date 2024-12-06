package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Atividade;
import com.example.demo.service.AtividadeService;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {


    @Autowired
    AtividadeService atividadeService;

    @PostMapping("/processar")
    public ResponseEntity<Map<String, List<Object>>> processarAtividades(@RequestBody List<Atividade> atividades) {
        Map<String, List<Object>> resultado = atividadeService.processarAtividades(atividades);
        return ResponseEntity.ok(resultado);
    }
}
