package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;
import com.igsi.encuestas.services.PreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas")
public class PreguntaController {

    private final PreguntaService service;

    public PreguntaController(PreguntaService service) {
        this.service = service;
    }

    // Listar todas las preguntas de una sección
    @GetMapping
    public ResponseEntity<List<PreguntaResponse>> getAll(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion
    ) {
        return ResponseEntity.ok(service.getAll(idEncuesta, idSeccion));
    }

    // Obtener una pregunta específica
    @GetMapping("/{idPregunta}")
    public ResponseEntity<PreguntaResponse> getById(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        return service.getById(idEncuesta, idSeccion, idPregunta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva pregunta
    @PostMapping
    public ResponseEntity<PreguntaResponse> create(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @RequestBody PreguntaRequest request
    ) {
        PreguntaResponse response = service.save(idEncuesta, idSeccion, request);
        return ResponseEntity.ok(response);
    }

    // Actualizar una pregunta
    @PutMapping("/{idPregunta}")
    public ResponseEntity<Void> update(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta,
            @RequestBody PreguntaRequest request
    ) {
        boolean updated = service.update(idEncuesta, idSeccion, idPregunta, request);
        return updated ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Eliminar una pregunta
    @DeleteMapping("/{idPregunta}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        boolean deleted = service.delete(idEncuesta, idSeccion, idPregunta);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
