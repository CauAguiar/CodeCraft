package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Projeto;
import com.example.demo.repository.ProjetoRepository;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> getProjetosByModuloId(Long moduloId) {
        return projetoRepository.findByModuloId(moduloId);
    }
}
