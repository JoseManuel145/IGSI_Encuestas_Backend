package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.departamentos.request.DepartamentoRequest;
import com.igsi.encuestas.dto.departamentos.response.DepartamentoResponse;
import com.igsi.encuestas.services.DepartamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoService service;
    public DepartamentoController(DepartamentoService departamentoService) {
        this.service = departamentoService;
    }
// LISTAR DEPARTAMENTOS
    @GetMapping
    public ResponseEntity<List<DepartamentoResponse>> getAll() {
        List<DepartamentoResponse> departamentos = service.getAll();
        return ResponseEntity.ok(departamentos);
    }
// OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> getById(@PathVariable Long id) {
        DepartamentoResponse departamento = service.getById(id);
        return ResponseEntity.ok(departamento);
    }
// CREAR DEPARTAMENTO
    @PostMapping
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<DepartamentoResponse> save(@RequestBody DepartamentoRequest dto) {
        DepartamentoResponse created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
// ACTUALIZAR DEPARTAMENTO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DepartamentoRequest dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }
// ELIMINAR DEPARTAMENTO (hard delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
// SOFT DELETE DEPARTAMENTO
    @PatchMapping("/{id}/soft-delete")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }
// RESTAURAR UN DEPARTAMENTO
    @PatchMapping("/{id}/restaurar")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> restaurar(@PathVariable Long id) {
        service.restaurar(id);
        return ResponseEntity.noContent().build();
    }
}