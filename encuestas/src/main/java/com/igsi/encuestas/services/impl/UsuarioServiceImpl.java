package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.usuarios.*;
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
    //  Inyeccion de Dependencias del repositorio
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository){
        this.repository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
//  Mapeo de UsuarioModel a UsuarioDto
    private UsuarioDto mapToDto(UsuarioModel usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setIdDepartamento(usuario.getIdDepartamento());
        return dto;
    }
    @Override
    public List<UsuarioDto> getAll() {
        return repository.getAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<UsuarioDto> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToDto);
    }
    @Override
    public Optional<UsuarioDto> getByCorreo(String correo) {
        return repository.getByCorreo(correo)
                .map(this::mapToDto);
    }
    @Override
    public UsuarioDto save(UsuarioCreateDto usuarioCreateDto) {
        // Hash de la contraseña
        String hashedPassword = passwordEncoder.encode(usuarioCreateDto.getPassword());

        UsuarioModel usuario = new UsuarioModel(
                null,
                usuarioCreateDto.getNombre(),
                usuarioCreateDto.getCorreo(),
                hashedPassword,
                usuarioCreateDto.getRol(),
                usuarioCreateDto.getIdDepartamento()
        );
        repository.saveUser(usuario);

        return mapToDto(usuario);
    }
    @Override
    public boolean update(Long id, UsuarioUpdateDto usuarioUpdateDto) {
        Optional<UsuarioModel> existing = repository.getById(id);
        if (existing.isEmpty()) return false;

        UsuarioModel usuario = existing.get();
        usuario.setNombre(usuarioUpdateDto.getNombre());
        usuario.setCorreo(usuarioUpdateDto.getCorreo());

        if (usuarioUpdateDto.getPassword() != null && !usuarioUpdateDto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioUpdateDto.getPassword()));
        }

        usuario.setIdDepartamento(usuarioUpdateDto.getIdDepartamento());

        return repository.updateUser(usuario) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
    @Override
    public Optional<UsuarioLoginResponseDto> login(UsuarioLoginDto loginDto) {

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Optional<UsuarioModel> usuarioOpt = repository.getByCorreo(loginDto.getCorreo());

        if (usuarioOpt.isEmpty()) return Optional.empty();

        UsuarioModel usuario = usuarioOpt.get();

        // Verifica contraseña
        if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
            return Optional.empty();
        }
        // Genera JWT
        String token = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("rol", usuario.getRol())
                .claim("id", usuario.getIdUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();

        UsuarioLoginResponseDto response = new UsuarioLoginResponseDto(
                usuario.getCorreo(),
                usuario.getRol(),
                token
        );
        return Optional.of(response);
    }
}