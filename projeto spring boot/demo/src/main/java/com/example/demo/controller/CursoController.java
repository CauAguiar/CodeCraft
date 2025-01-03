package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Curso;
import com.example.demo.service.CursoService;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Endpoint para retornar todos os cursos
    @GetMapping("/api/curso/getAll")
    public List<Curso> getCursos() {
        return cursoService.getAllCursos();
    }
}
