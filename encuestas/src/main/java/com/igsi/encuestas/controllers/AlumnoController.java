package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.alumnos.request.AlumnoRequest;
import com.igsi.encuestas.dto.alumnos.response.AlumnoIdResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoLoginResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoResponse;
import com.igsi.encuestas.services.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService service;
    public AlumnoController(AlumnoService alumnoService) {
        this.service = alumnoService;
    }
// LISTAR ALUMNOS
    @GetMapping
    public ResponseEntity<List<AlumnoResponse>> getAll() {
        List<AlumnoResponse> alumnos = service.getAll();
        return ResponseEntity.ok(alumnos);
    }
// OBTENER ALUMNO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoIdResponse> getById(@PathVariable Long id) {
        Optional<AlumnoIdResponse> alumnoOpt = service.getById(id);
        return alumnoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
// OBTENER ALUMNO POR NOMBRE
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<AlumnoResponse> getByNombre(@PathVariable String nombre) {
        Optional<AlumnoResponse> alumnoOpt = service.getByNombre(nombre);
        return alumnoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
// REGISTRAR UN ALUMNO
    @PostMapping
    public ResponseEntity<AlumnoResponse> save(@RequestBody AlumnoRequest request) {
        AlumnoResponse created = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
// ELIMINAR UN ALUMNO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
// LOGIN
    @PostMapping("/login")
    public ResponseEntity<AlumnoLoginResponse> login(@RequestBody AlumnoRequest request) {
        Optional<AlumnoLoginResponse> tokenOpt = service.login(request);
        return tokenOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}