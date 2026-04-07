package com.academico.sistema.service;

import com.academico.sistema.model.Docente;
import com.academico.sistema.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para Docente.
 * Contiene la lógica de negocio y se comunica con el repositorio.
 */
@Service
@Transactional
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> listarTodos() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> buscarPorId(Long id) {
        return docenteRepository.findById(id);
    }

    public Optional<Docente> buscarPorEmail(String email) {
        return docenteRepository.findByEmail(email);
    }

    public Docente guardar(Docente docente) {
        return docenteRepository.save(docente);
    }

    public Docente actualizar(Long id, Docente docenteActualizado) {
        return docenteRepository.findById(id)
            .map(docente -> {
                docente.setNombre(docenteActualizado.getNombre());
                docente.setApellido(docenteActualizado.getApellido());
                docente.setEmail(docenteActualizado.getEmail());
                docente.setEspecialidad(docenteActualizado.getEspecialidad());
                return docenteRepository.save(docente);
            })
            .orElseThrow(() -> new RuntimeException("Docente no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        docenteRepository.deleteById(id);
    }
}
