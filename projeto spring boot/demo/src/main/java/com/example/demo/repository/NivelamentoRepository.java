package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Nivelamento;

public interface NivelamentoRepository extends JpaRepository<Nivelamento, Long> {
    Optional<Nivelamento> findByCursoId(Long cursoId);
}

