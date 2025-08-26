package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.PreguntaModel;
import com.igsi.encuestas.repositories.PreguntaRepository;
import com.igsi.encuestas.services.PreguntaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository repository;
    public PreguntaServiceImpl(PreguntaRepository preguntaRepository) {
        this.repository = preguntaRepository;
    }
    private PreguntaResponse mapToResponse(PreguntaModel pregunta) {
        PreguntaResponse response = new PreguntaResponse();
        response.setIdPregunta(pregunta.getIdPregunta());
        response.setIdSeccion(pregunta.getIdSeccion());
        response.setTextoPregunta(pregunta.getTextoPregunta());
        response.setIdTipoPregunta(pregunta.getIdTipoPregunta());
        response.setOrden(pregunta.getOrden());
        response.setAyuda(pregunta.getAyuda());
        response.setPuntaje(pregunta.getPuntaje());
        return response;
    }
    @Override
    public List<PreguntaResponse> getAll(Long idEncuesta, Long idSeccion) {
        return repository.getAll(idSeccion).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public PreguntaResponse getById(Long idEncuesta, Long idSeccion, Long idPregunta) {
        PreguntaModel pregunta = repository.getById(idSeccion, idPregunta)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pregunta con id " + idPregunta + " no encontrada en la sección " + idSeccion
                ));
        return mapToResponse(pregunta);
    }
    @Override
    public PreguntaResponse save(Long idEncuesta, Long idSeccion, PreguntaRequest request) {
        if (request.getTextoPregunta() == null || request.getTextoPregunta().isBlank()) {
            throw new IllegalArgumentException("El texto de la pregunta no puede estar vacío");
        }
        PreguntaModel model = new PreguntaModel(
                null,
                idSeccion,
                request.getTextoPregunta(),
                request.getIdTipoPregunta(),
                request.getOrden(),
                request.getAyuda(),
                request.getPuntaje()
        );
        Long id = repository.save(model);
        model.setIdPregunta(id);
        return mapToResponse(model);
    }
    @Override
    public boolean update(Long idEncuesta, Long idSeccion, Long idPregunta, PreguntaRequest request) {
        PreguntaModel existing = repository.getById(idSeccion, idPregunta)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pregunta con id " + idPregunta + " no encontrada en la sección " + idSeccion
                ));
        existing.setTextoPregunta(request.getTextoPregunta());
        existing.setIdTipoPregunta(request.getIdTipoPregunta());
        existing.setOrden(request.getOrden());
        existing.setAyuda(request.getAyuda());
        existing.setPuntaje(request.getPuntaje());

        int updated = repository.update(idPregunta, existing);
        if (updated <= 0) {
            throw new IllegalStateException("No se pudo actualizar la pregunta con id " + idPregunta);
        }
        return true;
    }
    @Override
    public boolean delete(Long idEncuesta, Long idSeccion, Long idPregunta) {
        int deleted = repository.delete(idPregunta);
        if (deleted <= 0) {
            throw new ResourceNotFoundException(
                    "Pregunta con id " + idPregunta + " no encontrada o no pudo eliminarse"
            );
        }
        return true;
    }
}