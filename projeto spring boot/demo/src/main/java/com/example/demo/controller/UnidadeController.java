package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unidade")
public class UnidadeController {
    // @Autowired
    // private UnidadeService unidadeService;

    // @PostMapping("/getAll")
    // public ResponseEntity<List<Unidade>> getUnidadesByCurso(@RequestBody Map<String, String> requestBody) {
    //     // Pegando o nome do curso que foi passado no corpo da requisição
    //     String nomeCurso = requestBody.get("nomeCurso");

    //     // Verifica se o nome do curso foi fornecido
    //     if (nomeCurso == null || nomeCurso.trim().isEmpty()) {
    //         return ResponseEntity.badRequest().body(null); // Retorna erro 400 se nomeCurso for nulo ou vazio
    //     }

    //     // Recupera as unidades associadas ao curso
    //     List<Unidade> unidades = unidadeService.getUnidadesByCursoNome(nomeCurso);

    //     // Verifica se foram encontradas unidades
    //     if (unidades != null && !unidades.isEmpty()) {
    //         return ResponseEntity.ok(unidades); // Retorna as unidades se forem encontradas
    //     } else {
    //         return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar unidades
    //     }
    // }
}
