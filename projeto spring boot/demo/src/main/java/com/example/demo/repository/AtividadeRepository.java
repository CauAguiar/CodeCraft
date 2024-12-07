package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    // Adicione métodos personalizados, se necessário
}
