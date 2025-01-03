package com.example.demo.controller;

import com.example.demo.model.Modulo;
import com.example.demo.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modulo")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @GetMapping("/getByUnidadeIds")
    public List<Modulo> getModulosByUnidadeIds(@RequestParam("unidade_ids") List<Long> unidadeIds) {
        return moduloService.getModulosByUnidadeIds(unidadeIds);
    }
}
