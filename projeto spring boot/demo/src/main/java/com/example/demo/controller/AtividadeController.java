package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Atividade;
import com.example.demo.service.AtividadeService;

@RestController
@RequestMapping("/api/atividade")
public class AtividadeController {


    @Autowired
    AtividadeService atividadeService;

    @GetMapping("/getByModuloId")
    public ResponseEntity<List<Atividade>> getAtividadesByModuloId(@RequestParam("moduloId") Long moduloId) {
        List<Atividade> atividades = atividadeService.getAtividadesByModuloId(moduloId);
        return ResponseEntity.ok(atividades);
    }

    // @PostMapping("/processar")
    // public ResponseEntity<Map<String, List<Object>>> processarAtividades(@RequestBody List<Atividade> atividades) {
    //     Map<String, List<Object>> resultado = atividadeService.processarAtividades(atividades);
    //     return ResponseEntity.ok(resultado);
    // }
}
