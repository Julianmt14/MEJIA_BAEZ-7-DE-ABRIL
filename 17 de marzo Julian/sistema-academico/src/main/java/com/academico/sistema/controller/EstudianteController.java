package com.academico.sistema.controller;

import com.academico.sistema.model.Estudiante;
import com.academico.sistema.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    /** GET /api/estudiantes — Listar todos */
    @GetMapping
    public ResponseEntity<List<Estudiante>> listarTodos() {
        return ResponseEntity.ok(estudianteService.listarTodos());
    }

    /** GET /api/estudiantes/{id} — Buscar por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarPorId(@PathVariable Long id) {
        return estudianteService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/estudiantes — Crear estudiante */
    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante estudiante) {
        Estudiante nuevo = estudianteService.guardar(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /** PUT /api/estudiantes/{id} — Actualizar */
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Estudiante actualizado = estudianteService.actualizar(id, estudiante);
        return ResponseEntity.ok(actualizado);
    }

    /** POST /api/estudiantes/{estudianteId}/matricular/{cursoId} — Matricular en curso */
    @PostMapping("/{estudianteId}/matricular/{cursoId}")
    public ResponseEntity<Estudiante> matricular(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        Estudiante estudiante = estudianteService.matricularEnCurso(estudianteId, cursoId);
        return ResponseEntity.ok(estudiante);
    }

    /** DELETE /api/estudiantes/{estudianteId}/desmatricular/{cursoId} — Desmatricular */
    @DeleteMapping("/{estudianteId}/desmatricular/{cursoId}")
    public ResponseEntity<Estudiante> desmatricular(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        Estudiante estudiante = estudianteService.desmatricularDeCurso(estudianteId, cursoId);
        return ResponseEntity.ok(estudiante);
    }

    /** DELETE /api/estudiantes/{id} — Eliminar */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
