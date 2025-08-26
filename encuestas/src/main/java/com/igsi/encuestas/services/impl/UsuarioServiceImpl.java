package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.usuarios.request.UsuarioLoginRequest;
import com.igsi.encuestas.dto.usuarios.request.UsuarioRequest;
import com.igsi.encuestas.dto.usuarios.response.UsuarioLoginResponse;
import com.igsi.encuestas.dto.usuarios.response.UsuarioResponse;
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
import java.util.Optional;
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
// Mapea UsuarioModel -> UsuarioResponse
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
        return repository.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<UsuarioResponse> getById(Long id) {
        return repository.getById(id).map(this::mapToResponse);
    }
    @Override
    public Optional<UsuarioResponse> getByCorreo(String correo) {
        return repository.getByCorreo(correo).map(this::mapToResponse);
    }
    @Override
    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        String hashedPassword = passwordEncoder.encode(usuarioRequest.getPassword());

        UsuarioModel usuario = new UsuarioModel(
                null,
                usuarioRequest.getNombre(),
                usuarioRequest.getCorreo(),
                hashedPassword,
                usuarioRequest.getRol(),
                usuarioRequest.getIdDepartamento()
        );
        Long iDGenerado = repository.saveUser(usuario);
        usuario.setIdUsuario(iDGenerado);
        return mapToResponse(usuario);
    }
    @Override
    public boolean update(Long id, UsuarioRequest usuarioRequest) {
        Optional<UsuarioModel> existing = repository.getById(id);
        if (existing.isEmpty()) return false;

        UsuarioModel usuario = existing.get();
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setCorreo(usuarioRequest.getCorreo());
        if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        }
        usuario.setRol(usuarioRequest.getRol());
        usuario.setIdDepartamento(usuarioRequest.getIdDepartamento());

        return repository.updateUser(usuario) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
    @Override
    public Optional<UsuarioLoginResponse> login(UsuarioLoginRequest loginRequest) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Optional<UsuarioModel> usuarioOpt = repository.getByCorreo(loginRequest.getCorreo());
        if (usuarioOpt.isEmpty()) return Optional.empty();

        UsuarioModel usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return Optional.empty();
        }
        String token = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("rol", usuario.getRol())
                .claim("id", usuario.getIdUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();

        UsuarioLoginResponse response = new UsuarioLoginResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getIdDepartamento(),
                token
        );
        return Optional.of(response);
    }
}