package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.SeccionEncuestaModel;
import com.igsi.encuestas.repositories.SeccionEncuestaRepository;
import com.igsi.encuestas.services.SeccionEncuestaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeccionEncuestaServiceImpl implements SeccionEncuestaService {

    private final SeccionEncuestaRepository repository;
    public SeccionEncuestaServiceImpl(SeccionEncuestaRepository repository) {
        this.repository = repository;
    }
    private SeccionEncuestaResponse mapToResponse(SeccionEncuestaModel model) {
        SeccionEncuestaResponse response = new SeccionEncuestaResponse();
        response.setIdSeccion(model.getIdSeccion());
        response.setIdEncuesta(model.getIdEncuesta());
        response.setTitulo(model.getTitulo());
        response.setDescripcion(model.getDescripcion());
        response.setOrden(model.getOrden());
        return response;
    }
    @Override
    public List<SeccionEncuestaResponse> getAll(Long idEncuesta) {
        List<SeccionEncuestaModel> secciones = repository.getAll(idEncuesta);
        if (secciones.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron secciones para la encuesta " + idEncuesta);
        }
        return secciones.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public SeccionEncuestaResponse getById(Long idEncuesta, Long idSeccion) {
        SeccionEncuestaModel seccion = repository.getById(idEncuesta, idSeccion)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sección con id " + idSeccion + " no encontrada en la encuesta " + idEncuesta
                ));
        return mapToResponse(seccion);
    }
    @Override
    public Long save(SeccionEncuestaResponse seccion) {
        if (seccion.getTitulo() == null || seccion.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la sección no puede estar vacío");
        }
        SeccionEncuestaModel model = new SeccionEncuestaModel(
                null,
                seccion.getIdEncuesta(),
                seccion.getTitulo(),
                seccion.getDescripcion(),
                seccion.getOrden()
        );
        Long idGenerado = repository.save(model);
        model.setIdSeccion(idGenerado);
        return idGenerado;
    }
    @Override
    public boolean update(Long idEncuesta, Long idSeccion, SeccionEncuestaResponse seccion) {
        SeccionEncuestaModel existing = repository.getById(idEncuesta, idSeccion)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sección con id " + idSeccion + " no encontrada en la encuesta " + idEncuesta
                ));

        existing.setTitulo(seccion.getTitulo());
        existing.setDescripcion(seccion.getDescripcion());
        existing.setOrden(seccion.getOrden());

        int updated = repository.update(idSeccion, existing);
        if (updated <= 0) {
            throw new IllegalStateException("No se pudo actualizar la sección con id " + idSeccion);
        }
        return true;
    }
    @Override
    public boolean delete(Long idEncuesta, Long idSeccion) {
        int deleted = repository.delete(idSeccion);
        if (deleted <= 0) {
            throw new ResourceNotFoundException(
                    "Sección con id " + idSeccion + " no encontrada o no pudo eliminarse"
            );
        }
        return true;
    }
}