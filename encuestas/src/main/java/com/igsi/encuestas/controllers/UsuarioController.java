package com.igsi.encuestas.controllers;

import com.igsi.encuestas.dto.usuarios.*;
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
    public ResponseEntity<List<UsuarioDto>> getAll() {
        List<UsuarioDto> usuarios = service.getAll();
        return ResponseEntity.ok(usuarios);
    }
    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Long id) {
        Optional<UsuarioDto> usuarioOpt = service.getById(id);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // OBTENER POR CORREO
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDto> getByCorreo(@PathVariable String correo) {
        Optional<UsuarioDto> usuarioOpt = service.getByCorreo(correo);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // CREAR USUARIO
    @PostMapping
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioCreateDto usuarioCreateDto) {
        UsuarioDto nuevoUsuario = service.save(usuarioCreateDto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AdminGeneral')")
    public ResponseEntity<UsuarioDto> update(@PathVariable Long id,
                                             @RequestBody UsuarioUpdateDto usuarioUpdateDto) {
        boolean actualizado = service.update(id, usuarioUpdateDto);
        if (!actualizado) return ResponseEntity.notFound().build();

        Optional<UsuarioDto> usuarioOpt = service.getById(id);
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
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody UsuarioLoginDto loginDto) {
        Optional<UsuarioLoginResponseDto> tokenOpt = service.login(loginDto);
        return tokenOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
