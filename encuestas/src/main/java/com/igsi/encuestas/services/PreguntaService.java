package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;

import java.util.List;

public interface PreguntaService {
    List<PreguntaResponse> getAll(Long idEncuesta, Long idSeccion);
    PreguntaResponse getById(Long idEncuesta, Long idSeccion, Long idPregunta);
    PreguntaResponse save(Long idEncuesta, Long idSeccion, PreguntaRequest request);
    boolean update(Long idEncuesta, Long idSeccion, Long idPregunta, PreguntaRequest request);
    boolean delete(Long idEncuesta, Long idSeccion, Long idPregunta);
}