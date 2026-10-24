package com.example.laboratorio4.repository;

import com.example.laboratorio4.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository <Alumno, Long> {

}
