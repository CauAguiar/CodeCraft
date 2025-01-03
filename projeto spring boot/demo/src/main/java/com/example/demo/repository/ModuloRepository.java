package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    @Query("SELECT m FROM Modulo m WHERE m.unidadeId IN :unidadeIds")
    List<Modulo> findByUnidadeIds(@Param("unidadeIds") List<Long> unidadeIds);
}
