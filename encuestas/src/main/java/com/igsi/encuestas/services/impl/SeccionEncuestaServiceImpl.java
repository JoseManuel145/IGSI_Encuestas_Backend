package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;
import com.igsi.encuestas.models.SeccionEncuestaModel;
import com.igsi.encuestas.repositories.SeccionEncuestaRepository;
import com.igsi.encuestas.services.SeccionEncuestaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return repository.getAll(idEncuesta)
                .stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public Optional<SeccionEncuestaResponse> getById(Long idEncuesta, Long idSeccion) {
        return repository.getById(idEncuesta, idSeccion)
                .map(this::mapToResponse);
    }
    @Override
    public Long save(SeccionEncuestaResponse seccion) {
        // Convertir response a model antes de guardar
        SeccionEncuestaModel model = new SeccionEncuestaModel(
                null, // id autogenerado
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
    public int update(Long idEncuesta,Long idSeccion, SeccionEncuestaResponse seccion) {
        Optional<SeccionEncuestaModel> existing = repository.getById(idEncuesta, idSeccion);
        if (existing.isEmpty()) return 0;

        SeccionEncuestaModel model = existing.get();
        model.setTitulo(seccion.getTitulo());
        model.setDescripcion(seccion.getDescripcion());
        model.setOrden(seccion.getOrden());

        return repository.update(idSeccion, model);
    }
    @Override
    public int delete(Long idEncuesta, Long idSeccion) {
        return repository.delete(idSeccion);
    }
}