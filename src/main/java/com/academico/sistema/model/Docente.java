package com.academico.sistema.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Docente — representa un profesor.
 *
 * Relación: @OneToMany con Curso (un docente imparte muchos cursos).
 * mappedBy = "docente" indica que Curso es el dueño de la relación.
 */
@Entity
@Table(name = "docente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String especialidad;

    /**
     * @OneToMany: Un docente tiene muchos cursos.
     * mappedBy: el campo "docente" en la entidad Curso es el dueño.
     * cascade: las operaciones se propagan a los cursos.
     * orphanRemoval: si se quita un curso de la lista, se elimina de la BD.
     */
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Curso> cursos = new ArrayList<>();

    // Métodos helper para mantener la relación bidireccional
    public void addCurso(Curso curso) {
        cursos.add(curso);
        curso.setDocente(this);
    }

    public void removeCurso(Curso curso) {
        cursos.remove(curso);
        curso.setDocente(null);
    }
}
