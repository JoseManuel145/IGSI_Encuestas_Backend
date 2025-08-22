package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.response.SeccionEncuestaResponse;

import java.util.List;
import java.util.Optional;

public interface SeccionEncuestaService {

    List<SeccionEncuestaResponse> getAll(Long idEncuesta);

    Optional<SeccionEncuestaResponse> getById(Long idEncuesta, Long idSeccion);

    Long save(SeccionEncuestaResponse seccion);

    int update(Long idEncuesta, Long idSeccion, SeccionEncuestaResponse seccion);

    int delete(Long idEncuesta, Long idSeccion);


}
