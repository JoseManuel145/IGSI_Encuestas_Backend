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
        AlumnoIdResponse alumno = service.getById(id);
        return ResponseEntity.ok(alumno);
    }
// OBTENER ALUMNO POR NOMBRE
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<AlumnoResponse> getByNombre(@PathVariable String nombre) {
        AlumnoResponse alumno = service.getByNombre(nombre);
        return ResponseEntity.ok(alumno);
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
        service.delete(id); // Lanza excepción si no existe
        return ResponseEntity.noContent().build(); // 204
    }
// LOGIN
    @PostMapping("/login")
    public ResponseEntity<AlumnoLoginResponse> login(@RequestBody AlumnoRequest request) {
        AlumnoLoginResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }
}