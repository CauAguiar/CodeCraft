package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Unidade;
import com.example.demo.repository.UnidadeRepository;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    public List<Unidade> getUnidadesByLanguage(String languageName) {
            List<Unidade> unidades = unidadeRepository.findByCursoNome(languageName);
            
            // Imprime o resultado no log
            // if (unidades.isEmpty()) {
            //     System.out.println("Nenhuma unidade encontrada para o curso: " + languageName);
            // } else {
            //     System.out.println("Unidades encontradas: ");
            //     for (Unidade unidade : unidades) {
            //         System.out.println("ID: " + unidade.getId());
            //         System.out.println("Descrição: " + unidade.getDescricao());
            //         System.out.println("Título: " + unidade.getTitulo());
            //         System.out.println("Duração: " + unidade.getDuracaoTotal());
            //     }
            // }

            return unidades;
        }
}
