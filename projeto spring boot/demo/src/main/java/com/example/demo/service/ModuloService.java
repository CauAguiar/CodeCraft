package com.example.demo.service;

import com.example.demo.model.Modulo;
import com.example.demo.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public List<Modulo> getModulosByUnidadeIds(List<Long> unidadeIds) {
        return moduloRepository.findByUnidadeIds(unidadeIds);
    }
}
