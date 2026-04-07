package com.academico.sistema.service;

import com.academico.sistema.model.Curso;
import com.academico.sistema.model.Docente;
import com.academico.sistema.repository.CursoRepository;
import com.academico.sistema.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> listarPorDocente(Long docenteId) {
        return cursoRepository.findByDocenteId(docenteId);
    }

    public Curso guardar(Curso curso, Long docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado con id: " + docenteId));
        curso.setDocente(docente);
        return cursoRepository.save(curso);
    }

    public Curso actualizar(Long id, Curso cursoActualizado) {
        return cursoRepository.findById(id)
            .map(curso -> {
                curso.setNombre(cursoActualizado.getNombre());
                curso.setDescripcion(cursoActualizado.getDescripcion());
                curso.setCreditos(cursoActualizado.getCreditos());
                return cursoRepository.save(curso);
            })
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con id: " + id);
        }
        cursoRepository.deleteById(id);
    }
}
