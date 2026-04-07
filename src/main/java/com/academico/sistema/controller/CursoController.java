package com.academico.sistema.controller;

import com.academico.sistema.model.Curso;
import com.academico.sistema.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    /** GET /api/cursos — Listar todos los cursos */
    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    /** GET /api/cursos/{id} — Buscar curso por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/cursos/docente/{docenteId} — Cursos de un docente */
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Curso>> listarPorDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(cursoService.listarPorDocente(docenteId));
    }

    /** POST /api/cursos?docenteId=1 — Crear curso asignado a un docente */
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso, @RequestParam Long docenteId) {
        Curso nuevo = cursoService.guardar(curso, docenteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /** PUT /api/cursos/{id} — Actualizar un curso */
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso actualizado = cursoService.actualizar(id, curso);
        return ResponseEntity.ok(actualizado);
    }

    /** DELETE /api/cursos/{id} — Eliminar un curso */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
