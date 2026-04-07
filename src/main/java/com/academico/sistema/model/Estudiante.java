package com.academico.sistema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Estudiante — representa un alumno matriculado.
 *
 * Relaciones:
 *   @OneToOne con Carnet — cada estudiante tiene un único carnet.
 *   @ManyToMany con Curso — un estudiante cursa muchas materias.
 */
@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * @OneToOne: Un estudiante tiene un único carnet.
     * mappedBy: Carnet es el dueño de la relación (tiene la FK).
     * cascade: si se elimina el estudiante, se elimina su carnet.
     */
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Carnet carnet;

    /**
     * @ManyToMany: Un estudiante cursa muchos cursos.
     * @JoinTable: Define la tabla intermedia "estudiante_curso".
     *   joinColumns: FK hacia estudiante
     *   inverseJoinColumns: FK hacia curso
     * Este lado es el DUEÑO de la relación M:N.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estudiante_curso",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Curso> cursos = new ArrayList<>();

    // Métodos helper
    public void addCurso(Curso curso) {
        cursos.add(curso);
        curso.getEstudiantes().add(this);
    }

    public void removeCurso(Curso curso) {
        cursos.remove(curso);
        curso.getEstudiantes().remove(this);
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
        if (carnet != null) {
            carnet.setEstudiante(this);
        }
    }
}
