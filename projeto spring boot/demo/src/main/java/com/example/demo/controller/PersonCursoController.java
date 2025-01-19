package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/getByCursoIdAndPersonId")
    public ResponseEntity<?> getPersonCurso(@RequestParam(name = "personId") Long personId, @RequestParam(name = "cursoId") Long cursoId) {
        System.out.println("Receive person id: " + personId + " and curso id: " + cursoId);
        PersonCurso personCurso = personCursoService.findByIdPersonAndCurso(personId, cursoId);
        
        if(personCurso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PersonCurso not found");
        }
        
        return ResponseEntity.ok(personCurso);
    }

}
