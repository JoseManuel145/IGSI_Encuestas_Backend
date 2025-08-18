package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.alumnos.AlumnoDto;
import com.igsi.encuestas.services.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class AlumnoController {
    private final AlumnoService service;

    public AlumnoController(AlumnoService alumnoService) {
        this.service = alumnoService;
    }
//  LISTAR ALUMNOS (DEBUG)
    @GetMapping
    public ResponseEntity<List<AlumnoDto>> getAll() {
        List<AlumnoDto> alumnos = service.getAll();
        return ResponseEntity.ok(alumnos);
    }
//  OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> getById(@PathVariable Long id) {
        Optional<AlumnoDto> alumnoDto = service.getById(id);
        return alumnoDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//  REGISTRAR UN ALUMNO
    @PostMapping
    public ResponseEntity<AlumnoDto> save(@RequestBody AlumnoDto dto) {
        AlumnoDto created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
//  ELIMINAR UN ALUMNO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
