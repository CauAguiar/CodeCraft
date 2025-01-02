package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {


    // @Autowired
    // AtividadeService atividadeService;

    // @PostMapping("/processar")
    // public ResponseEntity<Map<String, List<Object>>> processarAtividades(@RequestBody List<Atividade> atividades) {
    //     Map<String, List<Object>> resultado = atividadeService.processarAtividades(atividades);
    //     return ResponseEntity.ok(resultado);
    // }
}
