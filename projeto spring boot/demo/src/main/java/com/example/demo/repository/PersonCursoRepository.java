package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PersonCurso;

@Repository
public interface PersonCursoRepository extends JpaRepository<PersonCurso, Long> {
    @Query(value = "SELECT * FROM person_curso WHERE id_person = :personId AND id_curso = :cursoId", nativeQuery = true)
    PersonCurso findByIdPersonAndCurso(@Param("personId") Long personId, @Param("cursoId") Long cursoId);
}
