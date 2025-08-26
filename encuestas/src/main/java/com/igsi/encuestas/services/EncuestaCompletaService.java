package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuestaCompleta.response.EncuestaCompletaResponse;

public interface EncuestaCompletaService {
    /**
     * Obtener toda la encuesta completa (secciones, preguntas, respuestas posibles) en un solo JSON
     * @param idEncuesta - Id de la encuesta
     * @return Encuesta completa
     */
    EncuestaCompletaResponse GetCompleta(Long idEncuesta);
}
