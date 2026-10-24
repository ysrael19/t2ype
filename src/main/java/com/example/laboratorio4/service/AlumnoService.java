package com.example.laboratorio4.service;

import com.example.laboratorio4.model.Alumno;
import com.example.laboratorio4.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<Alumno> listar() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        alumnos.forEach(this::calcularEstadoAcademico);
        return alumnos;
    }

    public Optional<Alumno> buscarPorId(Long id) {
        return alumnoRepository.findById(id).map(this::calcularEstadoAcademico);
    }

    public Alumno crear(Alumno alumno) {
        alumno.setId(null);
        return calcularEstadoAcademico(alumnoRepository.save(alumno));
    }

    public Optional<Alumno> actualizar(Long id, Alumno datos) {
        return alumnoRepository.findById(id).map(existente -> {
            existente.setNombres(datos.getNombres());
            existente.setCarrera(datos.getCarrera());
            existente.setCiclo(datos.getCiclo());
            existente.setPromedio(datos.getPromedio());
            return calcularEstadoAcademico(alumnoRepository.save(existente));
        });
    }

    public boolean eliminar(Long id) {
        if (!alumnoRepository.existsById(id)) {
            return false;
        }
        alumnoRepository.deleteById(id);
        return true;
    }

    // REGULAR si promedio >= 13, EN RIESGO si promedio < 13
    private Alumno calcularEstadoAcademico(Alumno alumno) {
        alumno.setEstadoAcademico(alumno.getPromedio() >= 13 ? "REGULAR" : "EN RIESGO");
        return alumno;
    }
}
