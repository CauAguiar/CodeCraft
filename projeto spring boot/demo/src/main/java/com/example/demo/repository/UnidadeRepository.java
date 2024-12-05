package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Curso;
import com.example.demo.model.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    List<Unidade> findByCurso(Curso curso, Sort sort);

}
