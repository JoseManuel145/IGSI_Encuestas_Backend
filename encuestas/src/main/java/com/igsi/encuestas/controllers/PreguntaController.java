package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;
import com.igsi.encuestas.services.PreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones/{idSeccion}/preguntas")
public class PreguntaController {

    private final PreguntaService service;
    public PreguntaController(PreguntaService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<PreguntaResponse>> getAll(
            @PathVariable Long idSeccion
    ) {
        return ResponseEntity.ok(service.getAll(idSeccion));
    }
    @GetMapping("/{idPregunta}")
    public ResponseEntity<PreguntaResponse> getById(
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        return ResponseEntity.ok(service.getById(idSeccion, idPregunta));
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<PreguntaResponse> create(
            @RequestBody PreguntaRequest request
    ) {
        PreguntaResponse response = service.save(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{idPregunta}")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> update(
            @PathVariable Long idPregunta,
            @RequestBody PreguntaRequest request
    ) {
        service.update(idPregunta, request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{idPregunta}")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> delete(
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta
    ) {
        service.delete(idSeccion, idPregunta);
        return ResponseEntity.noContent().build();
    }
}