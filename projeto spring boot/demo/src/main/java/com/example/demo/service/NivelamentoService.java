package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Nivelamento;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.NivelamentoRepository;

@Service
public class NivelamentoService {


    @Autowired
    private NivelamentoRepository nivelamentoRepository;

    @Autowired
    private CursoRepository cursoRepository; // Para verificar a existência do curso

    // Método para obter o nivelamento de um curso específico
    public Nivelamento getByIdCurso(Long cursoId) {
        // Verifica se o curso existe
        cursoRepository.findById(cursoId).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Busca o nivelamento associado ao cursoId
        return nivelamentoRepository.findByCursoId(cursoId)
                .orElseThrow(() -> new RuntimeException("Nivelamento não encontrado para o curso"));
    }

}