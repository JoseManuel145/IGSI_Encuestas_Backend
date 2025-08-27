package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.EncuestaRequest;
import com.igsi.encuestas.dto.encuesta.response.EncuestaResponse;
import com.igsi.encuestas.exceptions.BadRequestException;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.EncuestaModel;
import com.igsi.encuestas.repositories.EncuestaRepository;
import com.igsi.encuestas.services.EncuestaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncuestaServiceImpl implements EncuestaService {

    private final EncuestaRepository repository;
    public EncuestaServiceImpl(EncuestaRepository repository) {
        this.repository = repository;
    }
    private EncuestaResponse mapToResponse(EncuestaModel encuesta) {
        EncuestaResponse response = new EncuestaResponse();
        response.setIdEncuesta(encuesta.getIdEncuesta());
        response.setTitulo(encuesta.getTitulo());
        response.setDescripcion(encuesta.getDescripcion());
        response.setIdDepartamento(encuesta.getIdDepartamento());
        response.setFechaInicio(encuesta.getFechaInicio());
        response.setFechaFin(encuesta.getFechaFin());
        response.setEstado(encuesta.getEstado());
        response.setDeleted(encuesta.getDeleted());
        return response;
    }
    @Override
    public List<EncuestaResponse> getAll() {
        List<EncuestaModel> encuestas = repository.getAll();
        if (encuestas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron encuestas");
        }
        return encuestas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EncuestaResponse> getAllHabilitadas() {
        List<EncuestaModel> encuestas = repository.getAllHabilitadas();
        if (encuestas.isEmpty()) {
            throw new ResourceNotFoundException("No hay encuestas habilitadas");
        }
        return encuestas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public EncuestaResponse getById(Long id) {
        EncuestaModel encuesta = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encuesta con id " + id + " no encontrada"));
        return mapToResponse(encuesta);
    }
    @Override
    public List<EncuestaResponse> getByDepartamento(Long idDepartamento) {
        List<EncuestaModel> encuestas = repository.getByDepartamento(idDepartamento);
        if (encuestas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron encuestas para el departamento " + idDepartamento);
        }
        return encuestas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public EncuestaResponse save(EncuestaRequest request) {
        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
            throw new BadRequestException("El título de la encuesta es obligatorio");
        }
        EncuestaModel encuesta = new EncuestaModel(
                null,
                request.getTitulo(),
                request.getDescripcion(),
                request.getIdDepartamento(),
                request.getFechaInicio(),
                request.getFechaFin(),
                request.getEstado(),
                false
        );
        Long idGenerado = repository.save(encuesta);
        encuesta.setIdEncuesta(idGenerado);
        return mapToResponse(encuesta);
    }
    @Override
    public boolean update(Long id, EncuestaRequest request) {
        EncuestaModel encuesta = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encuesta con id " + id + " no encontrada"));

        encuesta.setTitulo(request.getTitulo());
        encuesta.setDescripcion(request.getDescripcion());
        encuesta.setIdDepartamento(request.getIdDepartamento());
        encuesta.setFechaInicio(request.getFechaInicio());
        encuesta.setFechaFin(request.getFechaFin());
        encuesta.setEstado(request.getEstado());

        boolean updated = repository.update(id, encuesta) > 0;
        if (!updated) {
            throw new ResourceNotFoundException("No se pudo actualizar la encuesta con id " + id);
        }
        return true;
    }
    @Override
    public boolean delete(Long id) {
        boolean deleted = repository.delete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Encuesta con id " + id + " no encontrada o no se pudo eliminar");
        }
        return true;
    }
    @Override
    public void softDelete(Long id) {
        boolean deleted = repository.softDelete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Encuesta con id " + id + " no encontrada o no se pudo deshabilitar");
        }
    }
    @Override
    public void restaurar(Long id) {
        boolean restored = repository.restaurar(id) > 0;
        if (!restored) {
            throw new ResourceNotFoundException("Encuesta con id " + id + " no encontrada o no se pudo restaurar");
        }
    }
}