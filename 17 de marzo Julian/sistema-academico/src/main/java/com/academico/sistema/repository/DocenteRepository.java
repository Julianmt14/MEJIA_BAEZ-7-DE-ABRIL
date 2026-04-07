package com.academico.sistema.repository;

import com.academico.sistema.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Docente.
 * JpaRepository proporciona métodos CRUD automáticamente:
 *   findAll(), findById(), save(), deleteById(), etc.
 */
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByEmail(String email);
}
