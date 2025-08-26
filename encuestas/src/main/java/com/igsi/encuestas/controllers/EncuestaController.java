package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.EncuestaRequest;
import com.igsi.encuestas.dto.encuesta.response.EncuestaResponse;
import com.igsi.encuestas.services.EncuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    private final EncuestaService service;
    public EncuestaController(EncuestaService encuestaService) {
        this.service = encuestaService;
    }
// LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<EncuestaResponse>> getAll() {
        List<EncuestaResponse> encuestas = service.getAll();
        return ResponseEntity.ok(encuestas);
    }
// OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponse> getById(@PathVariable Long id) {
        Optional<EncuestaResponse> encuestaOpt = service.getById(id);
        return encuestaOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
// OBTENER POR DEPARTAMENTO
    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<List<EncuestaResponse>> getByDepartamento(@PathVariable Long idDepartamento) {
        List<EncuestaResponse> encuestas = service.getByDepartamento(idDepartamento);
        return encuestas.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(encuestas);
    }
// CREAR ENCUESTA
    @PostMapping
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<EncuestaResponse> create(@RequestBody EncuestaRequest encuestaRequest) {
        EncuestaResponse nuevaEncuesta = service.save(encuestaRequest);
        return new ResponseEntity<>(nuevaEncuesta, HttpStatus.CREATED);
    }
// ACTUALIZAR ENCUESTA
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<EncuestaResponse> update(@PathVariable Long id,
                                                   @RequestBody EncuestaRequest encuestaRequest) {
        boolean actualizado = service.update(id, encuestaRequest);
        if (!actualizado) return ResponseEntity.notFound().build();

        Optional<EncuestaResponse> encuestaOpt = service.getById(id);
        return encuestaOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
// ELIMINAR ENCUESTA (hard delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = service.delete(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
// SOFT DELETE ENCUESTA
    @PatchMapping("/{id}/soft-delete")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        boolean eliminado = service.softDelete(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
// REGRESAR UNA ENCUESTA DEL SOFT-DELETE
    @PatchMapping("/{id}/restaurar")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> restaurar(@PathVariable Long id) {
        boolean eliminado = service.restaurar(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}