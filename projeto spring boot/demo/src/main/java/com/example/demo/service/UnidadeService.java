package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    // @Autowired
    // private UnidadeRepository unidadeRepository;

    // @Autowired
    // private CursoRepository cursoRepository;

    // Método para buscar unidades com base no nome do curso
    // public List<Unidade> getUnidadesByCursoNome(String nomeCurso) {
    //     Encontra o curso pelo nome
    //     Curso curso = cursoRepository.findByNome(nomeCurso);
    
    //     if (curso != null) {
    //         Retorna as unidades associadas a esse curso ordenadas pelo atributo 'ordem'
    //         return unidadeRepository.findByCurso(curso, Sort.by(Sort.Direction.ASC, "ordem"));
    //     }
    //     return null; // Caso o curso não seja encontrado
    // }
    
}
