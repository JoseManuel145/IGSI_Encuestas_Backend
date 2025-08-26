package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.RespuestaPosibleRequest;
import com.igsi.encuestas.dto.encuesta.response.RespuestaPosibleResponse;
import com.igsi.encuestas.services.RespuestaPosibleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas")
public class RespuestaPosibleController {

    private final RespuestaPosibleService service;

    public RespuestaPosibleController(RespuestaPosibleService service) {
        this.service = service;
    }
// Listar todas las respuestas posibles de una pregunta
    @GetMapping
    public ResponseEntity<List<RespuestaPosibleResponse>> getAll(@PathVariable Long idPregunta) {
        return ResponseEntity.ok(service.getAll(idPregunta));
    }
// Crear una nueva respuesta posible
    @PostMapping
    public ResponseEntity<RespuestaPosibleResponse> create(
            @PathVariable Long idPregunta,
            @RequestBody RespuestaPosibleRequest request
    ) {
        RespuestaPosibleResponse response = service.save(idPregunta, request);
        return ResponseEntity.ok(response);
    }
// Actualizar una respuesta existente
    @PutMapping("/{idRespuesta}")
    public ResponseEntity<Void> update(
            @PathVariable Long idPregunta,
            @PathVariable Long idRespuesta,
            @RequestBody RespuestaPosibleRequest request
    ) {
        boolean updated = service.update(idPregunta, idRespuesta, request);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
// Eliminar una respuesta posible
    @DeleteMapping("/{idRespuesta}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idPregunta,
            @PathVariable Long idRespuesta
    ) {
        boolean deleted = service.delete(idPregunta, idRespuesta);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}