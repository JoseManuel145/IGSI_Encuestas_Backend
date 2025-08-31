package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.usuarios.request.UsuarioLoginRequest;
import com.igsi.encuestas.dto.usuarios.request.UsuarioRequest;
import com.igsi.encuestas.dto.usuarios.response.UsuarioLoginResponse;
import com.igsi.encuestas.dto.usuarios.response.UsuarioResponse;
import com.igsi.encuestas.exceptions.BadRequestException;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.UsuarioModel;
import com.igsi.encuestas.repositories.UsuarioRepository;
import com.igsi.encuestas.services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    private UsuarioResponse mapToResponse(UsuarioModel usuario) {
        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getIdDepartamento()
        );
    }
    @Override
    public List<UsuarioResponse> getAll() {
        List<UsuarioModel> usuarios = repository.getAll();
        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron usuarios registrados");
        }
        return usuarios.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public UsuarioResponse getById(Long id) {
        UsuarioModel usuario = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"));
        return mapToResponse(usuario);
    }
    @Override
    public UsuarioResponse getByCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new BadRequestException("Correo no puede estar vacío");
        }
        UsuarioModel usuario = repository.getByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con correo '" + correo + "' no encontrado"));
        return mapToResponse(usuario);
    }
    @Override
    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        if (usuarioRequest.getNombre() == null || usuarioRequest.getNombre().isBlank() ||
                usuarioRequest.getCorreo() == null || usuarioRequest.getCorreo().isBlank() ||
                usuarioRequest.getPassword() == null || usuarioRequest.getPassword().isBlank()) {
            throw new BadRequestException("Nombre, correo y contraseña son obligatorios");
        }

        String hashedPassword = passwordEncoder.encode(usuarioRequest.getPassword());

        String rol = (usuarioRequest.getRol() == null || usuarioRequest.getRol().isBlank())
                ? "Empleado"
                : usuarioRequest.getRol();
        UsuarioModel usuario = new UsuarioModel(
                null,
                usuarioRequest.getNombre(),
                usuarioRequest.getCorreo(),
                hashedPassword,
                rol,
                usuarioRequest.getIdDepartamento()
        );
        Long idGenerado = repository.saveUser(usuario);
        usuario.setIdUsuario(idGenerado);
        return mapToResponse(usuario);
    }
    @Override
    public boolean update(Long id, UsuarioRequest usuarioRequest) {
        UsuarioModel usuario = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"));

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setCorreo(usuarioRequest.getCorreo());
        if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        }
        usuario.setRol(usuarioRequest.getRol());
        usuario.setIdDepartamento(usuarioRequest.getIdDepartamento());

        boolean updated = repository.updateUser(usuario) > 0;
        if (!updated) throw new BadRequestException("No se pudo actualizar el usuario con id " + id);
        return true;
    }
    @Override
    public boolean delete(Long id) {
        boolean deleted = repository.delete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado o no pudo eliminarse");
        }
        return true;
    }
    @Override
    public UsuarioLoginResponse login(UsuarioLoginRequest loginRequest) {
        if (loginRequest.getCorreo() == null || loginRequest.getPassword() == null) {
            throw new BadRequestException("Correo y contraseña son obligatorios para iniciar sesión");
        }
        UsuarioModel usuario = repository.getByCorreo(loginRequest.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con correo '" + loginRequest.getCorreo() + "' no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new BadRequestException("Contraseña incorrecta");
        }
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("rol", usuario.getRol())
                .claim("id", usuario.getIdUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
        return new UsuarioLoginResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getIdDepartamento(),
                token
        );
    }
}