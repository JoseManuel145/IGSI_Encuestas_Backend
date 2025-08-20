package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.departamentos.request.DepartamentoRequest;
import com.igsi.encuestas.dto.departamentos.response.DepartamentoResponse;
import com.igsi.encuestas.services.DepartamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<DepartamentoResponse> departamentoDto = service.getById(id);
        return departamentoDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        boolean updated = service.update(id, dto);
        if (updated) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }
    // ELIMINAR DEPARTAMENTO PERMANENTEMENTE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
    // ELIMINAR DEPARTAMENTO LOGICAMENTE
    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        boolean deleted = service.softDelete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
