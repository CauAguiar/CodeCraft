package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Nivelamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NivelamentoRepository extends JpaRepository<Nivelamento, Long> {
    // Adicione métodos personalizados, se necessário
}

