package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.respuestas.request.RespuestaRequest;
import com.igsi.encuestas.dto.respuestas.response.RespuestaResponse;
import com.igsi.encuestas.services.RespuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{idAlumno}/encuestas/{idEncuesta}/secciones/{idSeccion}/preguntas/{idPregunta}/respuestas")
public class RespuestaController {

    private final RespuestaService service;
    public RespuestaController(RespuestaService service) {
        this.service = service;
    }
// Guardar la respuesta de un alumno para una pregunta específica
    @PostMapping
    public ResponseEntity<RespuestaResponse> save(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta,
            @RequestBody RespuestaRequest request
    ) {
        // Nos aseguramos de que la respuesta esté asociada a la pregunta correcta
        request.setId_pregunta(idPregunta);
        RespuestaResponse response = service.save(request);
        return ResponseEntity.ok(response);
    }
// Actualizar la respuesta de un alumno para esa pregunta
    @PutMapping("/{idRespuesta}")
    public ResponseEntity<Void> update(
            @PathVariable Long idEncuesta,
            @PathVariable Long idSeccion,
            @PathVariable Long idPregunta,
            @PathVariable Long idRespuesta,
            @RequestBody RespuestaRequest request
    ) {
        request.setId_pregunta(idPregunta);
        boolean updated = service.update(idRespuesta, request);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @PostMapping("/complete")
    public ResponseEntity<RespuestaResponse> completarEncuesta(
            @PathVariable Long idEncuesta,
            @PathVariable Long idAlumno,
            @PathVariable Long idPregunta
    ) {
        boolean complete = service.completarEncuesta(idEncuesta, idAlumno);
        return complete ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}