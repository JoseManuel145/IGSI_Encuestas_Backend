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
    @GetMapping
    public ResponseEntity<List<PreguntaResponse>> getAll(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion
    ) {
        return ResponseEntity.ok(service.getAll(idEncuesta, idSeccion));
    }
    @GetMapping("/{idPregunta}")
    public ResponseEntity<PreguntaResponse> getById(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        return ResponseEntity.ok(service.getById(idEncuesta, idSeccion, idPregunta));
    }
    @PostMapping
    public ResponseEntity<PreguntaResponse> create(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @RequestBody PreguntaRequest request
    ) {
        PreguntaResponse response = service.save(idEncuesta, idSeccion, request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{idPregunta}")
    public ResponseEntity<Void> update(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta,
            @RequestBody PreguntaRequest request
    ) {
        service.update(idEncuesta, idSeccion, idPregunta, request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{idPregunta}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        service.delete(idEncuesta, idSeccion, idPregunta);
        return ResponseEntity.noContent().build();
    }
}