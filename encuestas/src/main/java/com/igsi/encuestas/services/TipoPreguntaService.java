package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.request.TipoPreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.TipoPreguntaResponse;

import java.util.List;
import java.util.Optional;

public interface TipoPreguntaService {

    List<TipoPreguntaResponse> getAll();

    Optional<TipoPreguntaResponse> getById(Long id);

    TipoPreguntaResponse save(TipoPreguntaRequest tipoPregunta);

    boolean update(Long id, TipoPreguntaRequest tipoPregunta);

    boolean delete(Long id);
}
