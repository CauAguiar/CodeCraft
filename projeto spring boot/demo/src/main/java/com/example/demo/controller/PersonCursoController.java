package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PersonCurso;
import com.example.demo.service.PersonCursoService;

@RestController
@RequestMapping("/api/person-curso")
public class PersonCursoController {

    @Autowired
    private PersonCursoService personCursoService;

    @GetMapping("/getPersonCurso")
    public ResponseEntity<List<PersonCurso>> getPersonCurso(@RequestParam(name = "personId") Long personId, @RequestParam(name = "cursoId") Long cursoId) {
        System.out.println("Receive person id: " + personId + " and curso id: " + cursoId);
        List<PersonCurso> personCurso = personCursoService.findByIdPersonAndCurso(personId, cursoId);
        return ResponseEntity.ok(personCurso);
    }

}
