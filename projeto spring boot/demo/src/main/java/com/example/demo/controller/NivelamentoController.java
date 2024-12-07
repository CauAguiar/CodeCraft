package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Nivelamento;
import com.example.demo.service.NivelamentoService;

@RestController
@RequestMapping("/api/nivelamento")
public class NivelamentoController {
    @Autowired
    private NivelamentoService nivelamentoService;

    // Endpoint POST para buscar Nivelamento por cursoId
    @PostMapping("/getnivelamento")
    public Nivelamento getNivelamentoByCurso(@RequestBody Map<String, Long> requestBody) {
        Long cursoId = requestBody.get("cursoId"); // Obtém o cursoId do corpo da requisição
        if (cursoId == null) {
            throw new IllegalArgumentException("O campo cursoId é obrigatório");
        }
        return nivelamentoService.getByIdCurso(cursoId); // Chama o serviço para obter o nivelamento
    }
}
