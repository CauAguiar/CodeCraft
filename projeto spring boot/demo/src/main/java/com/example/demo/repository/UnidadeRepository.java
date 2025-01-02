package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Unidade;


public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    // List<Unidade> findByCurso(Curso curso, Sort sort);

}
