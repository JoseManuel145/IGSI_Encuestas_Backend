package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;
import com.igsi.encuestas.services.SeccionEncuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<SeccionEncuestaResponse> getById(@PathVariable Long idEncuesta, @PathVariable Long idSeccion) {
        Optional<SeccionEncuestaResponse> seccion = service.getById(idEncuesta, idSeccion);
        return seccion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Long> create(@PathVariable Long idEncuesta,
                                       @RequestBody SeccionEncuestaResponse request) {
        request.setIdEncuesta(idEncuesta); // asegurar vínculo con la encuesta
        Long idGenerado = service.save(request);
        return ResponseEntity.ok(idGenerado);
    }
    @PutMapping("/{idSeccion}")
    public ResponseEntity<Void> update(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @RequestBody SeccionEncuestaResponse request) {
        int updated = service.update(idEncuesta, idSeccion, request);
        if (updated > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{idSeccion}")
    public ResponseEntity<Void> delete(@PathVariable Long idEncuesta,
                                       @PathVariable Long idSeccion) {
        int deleted = service.delete(idEncuesta, idSeccion);
        if (deleted > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}