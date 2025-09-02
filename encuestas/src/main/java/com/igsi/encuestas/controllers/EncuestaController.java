package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.encuesta.request.EncuestaRequest;
import com.igsi.encuestas.dto.encuesta.response.EncuestaResponse;
import com.igsi.encuestas.services.EncuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    private final EncuestaService service;
    public EncuestaController(EncuestaService encuestaService) {
        this.service = encuestaService;
    }
// LISTAR TODAS LAS ENCUESTAS deleted = FALSE
    @GetMapping("/master")
    public ResponseEntity<List<EncuestaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
// LISTAR TODAS LAS ENCUESTAS deleted = TRUE
    @GetMapping("/deleted")
    public ResponseEntity<List<EncuestaResponse>> getAllDeleted() {
        return ResponseEntity.ok(service.getAllDeleted());
    }
//  ENDPOINT para mostrar las encuestas disponibles a los alumnos
    @GetMapping("/alumno/{idAlumno}")
    public ResponseEntity<List<EncuestaResponse>> getAllHabilitadas(@PathVariable Long idAlumno) {
        return ResponseEntity.ok(service.getAllHabilitadas(idAlumno));
    }
// OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
// OBTENER POR DEPARTAMENTO
    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<List<EncuestaResponse>> getByDepartamento(@PathVariable Long idDepartamento) {
        return ResponseEntity.ok(service.getByDepartamento(idDepartamento));
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
        service.update(id, encuestaRequest);
        return ResponseEntity.ok(service.getById(id));
    }
// ELIMINAR ENCUESTA (hard delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
// SOFT DELETE ENCUESTA
    @PatchMapping("/{id}/soft-delete")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }
// RESTAURAR ENCUESTA
    @PatchMapping("/{id}/restaurar")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<Void> restaurar(@PathVariable Long id) {
        service.restaurar(id);
        return ResponseEntity.noContent().build();
    }
}