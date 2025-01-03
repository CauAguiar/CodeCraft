package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Unidade;
import com.example.demo.service.UnidadeService;

@RestController
@RequestMapping("/api/unidade")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @GetMapping("/getByLanguage")
    public List<Unidade> getUnidadesByLanguage(@RequestParam("language") String languageName) {
        System.out.println("LanguageName: " + languageName);
        return unidadeService.getUnidadesByLanguage(languageName);
    }
}
