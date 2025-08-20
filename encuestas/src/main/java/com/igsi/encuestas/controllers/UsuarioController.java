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
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService usuarioService) {
        this.service = usuarioService;
    }

    // LISTAR USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        List<UsuarioResponse> usuarios = service.getAll();
        return ResponseEntity.ok(usuarios);
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id) {
        Optional<UsuarioResponse> usuarioOpt = service.getById(id);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // OBTENER POR CORREO
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponse> getByCorreo(@PathVariable String correo) {
        Optional<UsuarioResponse> usuarioOpt = service.getByCorreo(correo);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR USUARIO
    @PostMapping
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse nuevoUsuario = service.save(usuarioRequest);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id,
                                                  @RequestBody UsuarioRequest usuarioRequest) {
        boolean actualizado = service.update(id, usuarioRequest);
        if (!actualizado) return ResponseEntity.notFound().build();

        Optional<UsuarioResponse> usuarioOpt = service.getById(id);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = service.delete(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponse> login(@RequestBody UsuarioLoginRequest loginRequest) {
        Optional<UsuarioLoginResponse> tokenOpt = service.login(loginRequest);
        return tokenOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
