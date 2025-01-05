package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Projeto;
import com.example.demo.service.ProjetoService;

@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/getByModuloId")
    public ResponseEntity<List<Projeto>> getProjetosByModuloId(@RequestParam("moduloId") Long moduloId) {
        List<Projeto> projetos = projetoService.getProjetosByModuloId(moduloId);
        return ResponseEntity.ok(projetos);
    }
}
