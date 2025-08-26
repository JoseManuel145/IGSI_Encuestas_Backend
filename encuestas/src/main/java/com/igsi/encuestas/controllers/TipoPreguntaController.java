package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.TipoPreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.TipoPreguntaResponse;
import com.igsi.encuestas.services.TipoPreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-pregunta")
public class TipoPreguntaController {
    private final TipoPreguntaService service;
    public TipoPreguntaController(TipoPreguntaService tipoPreguntaService) {
        this.service = tipoPreguntaService;
    }
// Obtener todos los tipos de pregunta
    @GetMapping
    public ResponseEntity<List<TipoPreguntaResponse>> getAll() {
        List<TipoPreguntaResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }
// Obtener un tipo de pregunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoPreguntaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
// Crear un nuevo tipo de pregunta
    @PostMapping
    public ResponseEntity<TipoPreguntaResponse> create(@RequestBody TipoPreguntaRequest request) {
        TipoPreguntaResponse created = service.save(request);
        return ResponseEntity.ok(created);
    }
// Actualizar un tipo de pregunta
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody TipoPreguntaRequest request) {
        boolean updated = service.update(id, request);
        if (updated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
// Eliminar un tipo de pregunta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}