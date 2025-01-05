package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExercicioAberto;
import com.example.demo.repository.ExercicioAbertoRepository;

@Service
public class ExercicioAbertoService {
    @Autowired
    private ExercicioAbertoRepository exercicioAbertoRepository;

    public List<ExercicioAberto> getExerciciosByModuloId(Long moduloId) {
        return exercicioAbertoRepository.findByModuloId(moduloId);
    }
}
