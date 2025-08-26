package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;
import com.igsi.encuestas.services.SeccionEncuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas/{idEncuesta}/secciones")
public class SeccionEncuestaController {

    private final SeccionEncuestaService service;
    public SeccionEncuestaController(SeccionEncuestaService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<SeccionEncuestaResponse>> getAll(@PathVariable Long idEncuesta) {
        return ResponseEntity.ok(service.getAll(idEncuesta));
    }
    @GetMapping("/{idSeccion}")
    public ResponseEntity<SeccionEncuestaResponse> getById(@PathVariable Long idEncuesta,
                                                           @PathVariable Long idSeccion) {
        return ResponseEntity.ok(service.getById(idEncuesta, idSeccion));
    }
    @PostMapping
    public ResponseEntity<Long> create(@PathVariable Long idEncuesta,
                                       @RequestBody SeccionEncuestaResponse request) {
        request.setIdEncuesta(idEncuesta);
        Long idGenerado = service.save(request);
        return ResponseEntity.ok(idGenerado);
    }
    @PutMapping("/{idSeccion}")
    public ResponseEntity<Void> update(@PathVariable Long idEncuesta,
                                       @PathVariable Long idSeccion,
                                       @RequestBody SeccionEncuestaResponse request) {
        service.update(idEncuesta, idSeccion, request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{idSeccion}")
    public ResponseEntity<Void> delete(@PathVariable Long idEncuesta,
                                       @PathVariable Long idSeccion) {
        service.delete(idEncuesta, idSeccion);
        return ResponseEntity.noContent().build();
    }
}