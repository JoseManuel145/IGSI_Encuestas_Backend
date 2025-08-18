package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.alumnos.AlumnoDto;
import com.igsi.encuestas.dto.alumnos.AlumnoLoginResponseDto;
import com.igsi.encuestas.models.AlumnoModel;
import com.igsi.encuestas.repositories.AlumnoRepository;
import com.igsi.encuestas.services.AlumnoService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.repository = alumnoRepository;
    }

    // Mapeo de AlumnoModel a AlumnoDto
    private AlumnoDto mapToDto(AlumnoModel alumno) {
        AlumnoDto dto = new AlumnoDto();
        dto.setNombre(alumno.getNombre());
        dto.setPassword(alumno.getPassword());
        return dto;
    }

    @Override
    public List<AlumnoDto> getAll() {
        return repository.getAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlumnoDto> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToDto);
    }

    @Override
    public Optional<AlumnoDto> getByNombre(String nombre) {
        return repository.getByNombre(nombre)
                .map(this::mapToDto);
    }

    @Override
    public AlumnoDto save(AlumnoDto alumnoCreateDto) {
        AlumnoModel alumno = new AlumnoModel(
                null,
                alumnoCreateDto.getNombre(),
                alumnoCreateDto.getPassword()
        );
        repository.saveAlumno(alumno);
        return mapToDto(alumno);
    }

    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }

    @Override
    public Optional<AlumnoLoginResponseDto> login(String nombre, String password) {
        // clave segura para firmar el token
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Optional<AlumnoModel> alumnoOpt = repository.getByNombre(nombre);

        if (alumnoOpt.isEmpty()) return Optional.empty();

        AlumnoModel alumno = alumnoOpt.get();

        // Validar contraseña (simple, sin hash)
        if (!alumno.getPassword().equals(password)) {
            return Optional.empty();
        }

        // Generar JWT
        String token = Jwts.builder()
                .setSubject(alumno.getNombre())
                .claim("id", alumno.getIdAlumno()) // puedes guardar el id del alumno
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();

        // Retornar DTO de respuesta
        AlumnoLoginResponseDto response = new AlumnoLoginResponseDto(
                alumno.getNombre(),
                token
        );

        return Optional.of(response);
    }

}
