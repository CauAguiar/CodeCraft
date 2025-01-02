package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Método para encontrar um curso pelo nome
    // Curso findByNome(String nome);
}

