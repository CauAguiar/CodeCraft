package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExercicioAberto;
import com.example.demo.service.ExercicioAbertoService;

@RestController
@RequestMapping("/api/exercicioaberto")
public class ExercicioAbertoController {
    @Autowired
    private ExercicioAbertoService exercicioAbertoService;

    @GetMapping("/getByModuloId")
    public ResponseEntity<List<ExercicioAberto>> getExerciciosAberto(@RequestParam("moduloId") Long moduloId) {
        List<ExercicioAberto> exercicios = exercicioAbertoService.getExerciciosByModuloId(moduloId);
        return ResponseEntity.ok(exercicios);
    }
}
