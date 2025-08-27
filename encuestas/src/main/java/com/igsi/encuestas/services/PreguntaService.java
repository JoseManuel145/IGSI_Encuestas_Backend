package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.request.PreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.PreguntaResponse;

import java.util.List;

public interface PreguntaService {
    List<PreguntaResponse> getAll(Long idSeccion);
    PreguntaResponse getById(Long idSeccion, Long idPregunta);
    PreguntaResponse save(PreguntaRequest request);
    boolean update(Long idPregunta, PreguntaRequest request);
    boolean delete(Long idSeccion, Long idPregunta);
}