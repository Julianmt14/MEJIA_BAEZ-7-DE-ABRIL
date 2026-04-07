package com.academico.sistema.repository;

import com.academico.sistema.model.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

    Optional<Carnet> findByEstudianteId(Long estudianteId);

    Optional<Carnet> findByCodigo(String codigo);
}
