package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.PersonCurso;
import com.example.demo.repository.PersonCursoRepository;

@Service
public class PersonCursoService {

    @Autowired
    private PersonCursoRepository personCursoRepository;

    public PersonCurso findByIdPersonAndCurso(Long personId, Long cursoId) {
        return personCursoRepository.findByIdPersonAndCurso(personId, cursoId);
    }
}
