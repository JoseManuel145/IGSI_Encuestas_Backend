package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.alumnos.request.AlumnoRequest;
import com.igsi.encuestas.dto.alumnos.response.AlumnoIdResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoLoginResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoResponse;
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
// Mapeos
    private AlumnoResponse mapToResponse(AlumnoModel alumno) {
        return new AlumnoResponse(alumno.getIdAlumno(), alumno.getNombre());
    }
    private AlumnoIdResponse mapToIdResponse(AlumnoModel alumno) {
        return new AlumnoIdResponse(alumno.getIdAlumno(), alumno.getNombre(), alumno.getPassword());
    }
    @Override
    public List<AlumnoResponse> getAll() {
        return repository.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<AlumnoIdResponse> getById(Long id) {
        return repository.getById(id).map(this::mapToIdResponse);
    }
    @Override
    public Optional<AlumnoResponse> getByNombre(String nombre) {
        return repository.getByNombre(nombre).map(this::mapToResponse);
    }
    @Override
    public AlumnoResponse save(AlumnoRequest alumnoRequest) {
        AlumnoModel alumno = new AlumnoModel(
                null, // id será generado por la BD
                alumnoRequest.getNombre(),
                alumnoRequest.getPassword()
        );
        // Guardar en BD y obtener el ID generado
        Long idGenerado = repository.save(alumno);
        alumno.setIdAlumno(idGenerado);
        // Mapear a DTO de respuesta
        return new AlumnoResponse(alumno.getIdAlumno(), alumno.getNombre());
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
    @Override
    public Optional<AlumnoLoginResponse> login(AlumnoRequest alumnoRequest) {
        // Crear clave segura de al menos 256 bits
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Optional<AlumnoModel> alumnoOpt = repository.getByNombre(alumnoRequest.getNombre());
        if (alumnoOpt.isEmpty()) return Optional.empty();

        AlumnoModel alumno = alumnoOpt.get();
        // Validar contraseña
        if (!alumno.getPassword().equals(alumnoRequest.getPassword())) {
            return Optional.empty();
        }
        // Generar JWT
        String token = Jwts.builder()
                .setSubject(alumno.getNombre())
                .claim("id", alumno.getIdAlumno())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();

        AlumnoLoginResponse response = new AlumnoLoginResponse(
                alumno.getIdAlumno(),
                alumno.getNombre(),
                token
        );
        return Optional.of(response);
    }
}