package com.academico.sistema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entidad Carnet — identificación del estudiante.
 *
 * Relación: @OneToOne con Estudiante.
 * Este lado es el DUEÑO de la relación (tiene la FK estudiante_id).
 */
@Entity
@Table(name = "carnet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carnet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    /**
     * @OneToOne: Un carnet pertenece a un único estudiante.
     * @JoinColumn: La columna "estudiante_id" es la FK.
     * unique = true: garantiza la relación 1:1 a nivel de BD.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", unique = true, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Estudiante estudiante;
}
