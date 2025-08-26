package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuestaCompleta.response.EncuestaCompletaResponse;
import com.igsi.encuestas.services.EncuestaCompletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaCompletaController {

    private final EncuestaCompletaService service;
    public EncuestaCompletaController(EncuestaCompletaService service) {
        this.service = service;
    }

    /**
     * Obtener la encuesta completa con todas sus secciones, preguntas y respuestas posibles.
     * URL: /api/encuestas/{id}/completa
     */
    @GetMapping("/{id}/completa")
    public ResponseEntity<EncuestaCompletaResponse> getEncuestaCompleta(@PathVariable Long id) {
        EncuestaCompletaResponse response = service.GetCompleta(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}