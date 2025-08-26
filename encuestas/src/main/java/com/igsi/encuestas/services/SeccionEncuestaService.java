package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;

import java.util.List;

public interface SeccionEncuestaService {

    List<SeccionEncuestaResponse> getAll(Long idEncuesta);

    SeccionEncuestaResponse getById(Long idEncuesta, Long idSeccion);

    Long save(SeccionEncuestaResponse seccion);

    boolean update(Long idEncuesta, Long idSeccion, SeccionEncuestaResponse seccion);

    boolean delete(Long idEncuesta, Long idSeccion);


}
