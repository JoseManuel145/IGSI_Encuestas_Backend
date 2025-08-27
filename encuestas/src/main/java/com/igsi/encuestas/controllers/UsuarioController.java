package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.usuarios.request.UsuarioLoginRequest;
import com.igsi.encuestas.dto.usuarios.request.UsuarioRequest;
import com.igsi.encuestas.dto.usuarios.response.UsuarioLoginResponse;
import com.igsi.encuestas.dto.usuarios.response.UsuarioResponse;
import com.igsi.encuestas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService usuarioService) {
        this.service = usuarioService;
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AdminGeneral','Empleado')")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponse> getByCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(service.getByCorreo(correo));
    }
    @PostMapping
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest usuarioRequest) {
        return new ResponseEntity<>(service.save(usuarioRequest), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id,
                                                  @RequestBody UsuarioRequest usuarioRequest) {
        service.update(id, usuarioRequest);
        return ResponseEntity.ok(service.getById(id));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponse> login(@RequestBody UsuarioLoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }
}