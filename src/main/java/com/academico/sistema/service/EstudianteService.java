package com.academico.sistema.service;

import com.academico.sistema.model.Curso;
import com.academico.sistema.model.Estudiante;
import com.academico.sistema.repository.CursoRepository;
import com.academico.sistema.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante actualizar(Long id, Estudiante estudianteActualizado) {
        return estudianteRepository.findById(id)
            .map(estudiante -> {
                estudiante.setNombre(estudianteActualizado.getNombre());
                estudiante.setApellido(estudianteActualizado.getApellido());
                estudiante.setEmail(estudianteActualizado.getEmail());
                estudiante.setFechaNacimiento(estudianteActualizado.getFechaNacimiento());
                return estudianteRepository.save(estudiante);
            })
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
    }

    /**
     * Matricular un estudiante en un curso (relación ManyToMany)
     */
    public Estudiante matricularEnCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + estudianteId));
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + cursoId));

        estudiante.addCurso(curso);
        return estudianteRepository.save(estudiante);
    }

    /**
     * Desmatricular un estudiante de un curso
     */
    public Estudiante desmatricularDeCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + estudianteId));
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + cursoId));

        estudiante.removeCurso(curso);
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }
}
