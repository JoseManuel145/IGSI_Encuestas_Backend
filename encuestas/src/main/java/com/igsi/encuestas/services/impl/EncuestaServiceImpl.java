package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.EncuestaRequest;
import com.igsi.encuestas.dto.encuesta.response.EncuestaResponse;
import com.igsi.encuestas.models.EncuestaModel;
import com.igsi.encuestas.repositories.EncuestaRepository;
import com.igsi.encuestas.services.EncuestaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EncuestaServiceImpl implements EncuestaService {

    private final EncuestaRepository repository;
    public EncuestaServiceImpl(EncuestaRepository repository) {
        this.repository = repository;
    }
// Mapea EncuestaModel a EncuestaResponse
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
        return repository.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<EncuestaResponse> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToResponse);
    }
    @Override
    public List<EncuestaResponse> getByDepartamento(Long idDepartamento) {
        return repository.getByDepartamento(idDepartamento).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public EncuestaResponse save(EncuestaRequest request) {
        // Convertir request a model
        EncuestaModel encuesta = new EncuestaModel(
                null, // se genera en la BD
                request.getTitulo(),
                request.getDescripcion(),
                request.getIdDepartamento(),
                request.getFechaInicio(),
                request.getFechaFin(),
                request.getEstado(),
                false // al crearla, no está eliminada
        );
        // Guardar en base de datos
        Long idGenerado = repository.save(encuesta);
        encuesta.setIdEncuesta(idGenerado);
        // Retornar como response
        return mapToResponse(encuesta);
    }
    @Override
    public boolean update(Long id, EncuestaRequest request) {
        Optional<EncuestaModel> existing = repository.getById(id);
        if (existing.isEmpty()) return false;

        EncuestaModel encuesta = existing.get();
        encuesta.setTitulo(request.getTitulo());
        encuesta.setDescripcion(request.getDescripcion());
        encuesta.setIdDepartamento(request.getIdDepartamento());
        encuesta.setFechaInicio(request.getFechaInicio());
        encuesta.setFechaFin(request.getFechaFin());
        encuesta.setEstado(request.getEstado());

        return repository.update(id, encuesta) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
    @Override
    public boolean softDelete(Long id) {
        return repository.softDelete(id) > 0;
    }
    @Override
    public boolean restaurar(Long id) {
        return repository.restaurar(id) > 0;
    }
}