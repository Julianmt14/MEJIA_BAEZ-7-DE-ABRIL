package com.academico.sistema.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Curso — representa una materia académica.
 *
 * Relaciones:
 *   @ManyToOne con Docente — muchos cursos pertenecen a un docente.
 *   @ManyToMany con Estudiante — un curso puede tener muchos estudiantes y viceversa.
 */
@Entity
@Table(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer creditos;

    /**
     * @ManyToOne: Muchos cursos pertenecen a un docente.
     * @JoinColumn: La columna "docente_id" en la tabla curso es la FK.
     * Este lado es el DUEÑO de la relación (tiene la FK).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Docente docente;

    /**
     * @ManyToMany: Un curso tiene muchos estudiantes.
     * mappedBy: Estudiante es el dueño de la relación M:N.
     */
    @ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Estudiante> estudiantes = new ArrayList<>();
}
