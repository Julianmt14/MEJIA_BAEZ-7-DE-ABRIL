package com.academico.sistema.service;

import com.academico.sistema.model.Carnet;
import com.academico.sistema.model.Estudiante;
import com.academico.sistema.repository.CarnetRepository;
import com.academico.sistema.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarnetService {

    @Autowired
    private CarnetRepository carnetRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Carnet> listarTodos() {
        return carnetRepository.findAll();
    }

    public Optional<Carnet> buscarPorId(Long id) {
        return carnetRepository.findById(id);
    }

    public Optional<Carnet> buscarPorEstudiante(Long estudianteId) {
        return carnetRepository.findByEstudianteId(estudianteId);
    }

    public Carnet guardar(Carnet carnet, Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + estudianteId));
        carnet.setEstudiante(estudiante);
        return carnetRepository.save(carnet);
    }

    public Carnet actualizar(Long id, Carnet carnetActualizado) {
        return carnetRepository.findById(id)
            .map(carnet -> {
                carnet.setCodigo(carnetActualizado.getCodigo());
                carnet.setFechaEmision(carnetActualizado.getFechaEmision());
                carnet.setFechaExpiracion(carnetActualizado.getFechaExpiracion());
                return carnetRepository.save(carnet);
            })
            .orElseThrow(() -> new RuntimeException("Carnet no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        carnetRepository.deleteById(id);
    }
}
