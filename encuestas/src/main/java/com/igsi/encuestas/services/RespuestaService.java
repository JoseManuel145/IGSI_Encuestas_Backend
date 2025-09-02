package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.respuestas.request.RespuestaRequest;
import com.igsi.encuestas.dto.respuestas.response.RespuestaEstadisticaResponse;
import com.igsi.encuestas.dto.respuestas.response.RespuestaResponse;

import java.util.List;
import java.util.Map;

public interface RespuestaService {

    RespuestaResponse save(RespuestaRequest request);
    List<Map<String,Object>> getAll(Long idAlumno, Long idEncuesta);
    boolean update(Long id, RespuestaRequest respuesta);
    boolean delete(Long id);
    List<RespuestaEstadisticaResponse> contarRespuestas(Long idPregunta);
    boolean guardarEncuestaCompleta(Long idAlumno, Long idEncuesta);
}
