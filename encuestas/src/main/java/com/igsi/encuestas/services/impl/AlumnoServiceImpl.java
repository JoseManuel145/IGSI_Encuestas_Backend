package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.alumnos.request.AlumnoRequest;
import com.igsi.encuestas.dto.alumnos.response.AlumnoIdResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoLoginResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoResponse;
import com.igsi.encuestas.exceptions.BadRequestException;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
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
    private AlumnoResponse mapToResponse(AlumnoModel alumno) {
        return new AlumnoResponse(alumno.getIdAlumno(), alumno.getNombre());
    }
    private AlumnoIdResponse mapToIdResponse(AlumnoModel alumno) {
        return new AlumnoIdResponse(alumno.getIdAlumno(), alumno.getNombre(), alumno.getPassword());
    }
    @Override
    public List<AlumnoResponse> getAll() {
        List<AlumnoModel> alumnos = repository.getAll();
        if (alumnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron alumnos registrados");
        }
        return alumnos.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public AlumnoIdResponse getById(Long id) {
        return repository.getById(id)
                .map(this::mapToIdResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno con id " + id + " no encontrado"));
    }
    @Override
    public AlumnoResponse getByNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nombre del alumno no puede estar vacío");
        }
        return repository.getByNombre(nombre)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno con nombre '" + nombre + "' no encontrado"));
    }
    @Override
    public AlumnoResponse save(AlumnoRequest alumnoRequest) {
        if (alumnoRequest.getNombre() == null || alumnoRequest.getNombre().isBlank()
                || alumnoRequest.getPassword() == null || alumnoRequest.getPassword().isBlank()) {
            throw new BadRequestException("Nombre y contraseña son obligatorios para registrar un alumno");
        }
        AlumnoModel alumno = new AlumnoModel(
                null,
                alumnoRequest.getNombre(),
                alumnoRequest.getPassword()
        );
        Long idGenerado = repository.save(alumno);
        alumno.setIdAlumno(idGenerado);

        return mapToResponse(alumno);
    }
    @Override
    public boolean delete(Long id) {
        boolean eliminado = repository.delete(id) > 0;
        if (!eliminado) {
            throw new ResourceNotFoundException("Alumno con id " + id + " no encontrado o no pudo eliminarse");
        }
        return true;
    }
    @Override
    public AlumnoLoginResponse login(AlumnoRequest alumnoRequest) {
        if (alumnoRequest.getNombre() == null || alumnoRequest.getPassword() == null) {
            throw new BadRequestException("Nombre y contraseña son obligatorios para iniciar sesión");
        }
        AlumnoModel alumno = repository.getByNombre(alumnoRequest.getNombre())
                .orElseThrow(() -> new ResourceNotFoundException("Alumno con nombre '" + alumnoRequest.getNombre() + "' no encontrado"));

        if (!alumno.getPassword().equals(alumnoRequest.getPassword())) {
            throw new BadRequestException("Contraseña incorrecta");
        }
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setSubject(alumno.getNombre())
                .claim("id", alumno.getIdAlumno())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
        return new AlumnoLoginResponse(alumno.getIdAlumno(), alumno.getNombre(), token);
    }
}