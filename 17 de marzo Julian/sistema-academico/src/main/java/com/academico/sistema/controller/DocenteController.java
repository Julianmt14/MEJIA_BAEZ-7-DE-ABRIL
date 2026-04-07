package com.academico.sistema.controller;

import com.academico.sistema.model.Docente;
import com.academico.sistema.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Docente — Capa Controller del patrón MVC.
 *
 * @RestController = @Controller + @ResponseBody
 * Todos los métodos devuelven datos (JSON) en lugar de vistas.
 */
@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    /** GET /api/docentes — Listar todos los docentes */
    @GetMapping
    public ResponseEntity<List<Docente>> listarTodos() {
        return ResponseEntity.ok(docenteService.listarTodos());
    }

    /** GET /api/docentes/{id} — Buscar docente por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Docente> buscarPorId(@PathVariable Long id) {
        return docenteService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/docentes — Crear un nuevo docente */
    @PostMapping
    public ResponseEntity<Docente> crear(@RequestBody Docente docente) {
        Docente nuevo = docenteService.guardar(docente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /** PUT /api/docentes/{id} — Actualizar un docente existente */
    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizar(@PathVariable Long id, @RequestBody Docente docente) {
        Docente actualizado = docenteService.actualizar(id, docente);
        return ResponseEntity.ok(actualizado);
    }

    /** DELETE /api/docentes/{id} — Eliminar un docente */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        docenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
