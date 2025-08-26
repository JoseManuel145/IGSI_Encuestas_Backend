package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;
import com.igsi.encuestas.models.PreguntaModel;
import com.igsi.encuestas.repositories.PreguntaRepository;
import com.igsi.encuestas.services.PreguntaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<PreguntaResponse> getById(Long idEncuesta, Long idSeccion, Long idPregunta) {
        return repository.getById(idSeccion, idPregunta).stream()
                .map(this::mapToResponse)
                .findFirst();
    }

    @Override
    public PreguntaResponse save(Long idEncuesta, Long idSeccion, PreguntaRequest request) {
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
        Optional<PreguntaModel> existente = repository.getById(idSeccion, idPregunta);
        if (existente.isEmpty()) return false;

        PreguntaModel model = existente.get();
        model.setIdSeccion(request.getIdSeccion());
        model.setTextoPregunta(request.getTextoPregunta());
        model.setOrden(request.getOrden());
        model.setAyuda(request.getAyuda());
        model.setPuntaje(request.getPuntaje());
        model.setIdTipoPregunta(request.getIdTipoPregunta());

        return repository.update(idPregunta, model) > 0;
    }

    @Override
    public boolean delete(Long idEncuesta, Long idSeccion, Long idPregunta) {
        Optional<PreguntaModel> existente = repository.getById(idSeccion, idPregunta);
        if (existente.isEmpty()) return false;
        return repository.delete(idPregunta) > 0;
    }
}
