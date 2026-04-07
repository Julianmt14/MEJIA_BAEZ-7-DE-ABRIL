package com.academico.sistema.controller;

import com.academico.sistema.model.Carnet;
import com.academico.sistema.service.CarnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carnets")
@CrossOrigin(origins = "*")
public class CarnetController {

    @Autowired
    private CarnetService carnetService;

    /** GET /api/carnets — Listar todos */
    @GetMapping
    public ResponseEntity<List<Carnet>> listarTodos() {
        return ResponseEntity.ok(carnetService.listarTodos());
    }

    /** GET /api/carnets/{id} — Buscar por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Carnet> buscarPorId(@PathVariable Long id) {
        return carnetService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/carnets/estudiante/{estudianteId} — Carnet de un estudiante */
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Carnet> buscarPorEstudiante(@PathVariable Long estudianteId) {
        return carnetService.buscarPorEstudiante(estudianteId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/carnets?estudianteId=1 — Crear carnet para un estudiante */
    @PostMapping
    public ResponseEntity<Carnet> crear(@RequestBody Carnet carnet, @RequestParam Long estudianteId) {
        Carnet nuevo = carnetService.guardar(carnet, estudianteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /** PUT /api/carnets/{id} — Actualizar */
    @PutMapping("/{id}")
    public ResponseEntity<Carnet> actualizar(@PathVariable Long id, @RequestBody Carnet carnet) {
        Carnet actualizado = carnetService.actualizar(id, carnet);
        return ResponseEntity.ok(actualizado);
    }

    /** DELETE /api/carnets/{id} — Eliminar */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carnetService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
