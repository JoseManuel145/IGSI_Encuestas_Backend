package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.request.RespuestaPosibleRequest;
import com.igsi.encuestas.dto.encuesta.response.RespuestaPosibleResponse;

import java.util.List;

public interface RespuestaPosibleService {
    List<RespuestaPosibleResponse> getAll(Long idPregunta);
    RespuestaPosibleResponse save(Long idPregunta, RespuestaPosibleRequest request);
    boolean update(Long idPregunta, Long idRespuesta, RespuestaPosibleRequest request);
    boolean delete(Long idPregunta, Long idRespuesta);
}
