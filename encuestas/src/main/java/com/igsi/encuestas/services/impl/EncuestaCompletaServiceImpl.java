package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuestaCompleta.response.*;
import com.igsi.encuestas.models.*;
import com.igsi.encuestas.repositories.EncuestaCompletaRepository;
import com.igsi.encuestas.services.EncuestaCompletaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncuestaCompletaServiceImpl implements EncuestaCompletaService {

    private final EncuestaCompletaRepository repository;
    public EncuestaCompletaServiceImpl(EncuestaCompletaRepository repository) {
        this.repository = repository;
    }
    // Mapea EncuestaModel a EncuestaCompletaResponse
    private EncuestaCompletaResponse mapToResponse(EncuestaModel encuesta) {
        EncuestaCompletaResponse response = new EncuestaCompletaResponse();
        response.setIdEncuesta(encuesta.getIdEncuesta());
        response.setTitulo(encuesta.getTitulo());
        response.setDescripcion(encuesta.getDescripcion());
        response.setIdDepartamento(encuesta.getIdDepartamento());
        response.setFechaInicio(encuesta.getFechaInicio());
        response.setFechaFin(encuesta.getFechaFin());
        response.setEstado(encuesta.getEstado());
        response.setDeleted(encuesta.getDeleted());
        // Secciones
        List<SeccionEncuestaModel> secciones = repository.getAllSecciones(encuesta.getIdEncuesta());

        List<SeccionResponse> seccionesResp = secciones.stream().map(seccion -> {
            SeccionResponse sResp = new SeccionResponse();
            sResp.setIdSeccion(seccion.getIdSeccion());
            sResp.setTitulo(seccion.getTitulo());
            sResp.setDescripcion(seccion.getDescripcion());
            sResp.setOrden(seccion.getOrden());

            // Preguntas
            List<PreguntaModel> preguntas = repository.getAllPreguntas(seccion.getIdSeccion());
            List<PreguntaResponse> preguntasResp = preguntas.stream().map(pregunta -> {
                PreguntaResponse pResp = new PreguntaResponse();
                pResp.setIdPregunta(pregunta.getIdPregunta());
                pResp.setTextoPregunta(pregunta.getTextoPregunta());
                pResp.setIdTipoPregunta(pregunta.getIdTipoPregunta());
                pResp.setOrden(pregunta.getOrden());
                pResp.setAyuda(pregunta.getAyuda());
                pResp.setPuntaje(pregunta.getPuntaje());

                // Respuestas posibles
                List<RespuestaPosibleModel> respuestas = repository.getAllRespuestas(pregunta.getIdPregunta());
                List<RespuestaPosibleResponse> respuestasResp = respuestas.stream().map(r -> {
                    RespuestaPosibleResponse rResp = new RespuestaPosibleResponse();
                    rResp.setIdRespuestaPosible(r.getIdRespuestaPosible());
                    rResp.setIdPregunta(r.getIdPregunta());
                    rResp.setTextoRespuesta(r.getTextoRespuesta());
                    rResp.setPuntaje(r.getPuntaje());
                    rResp.setEsCorrecta(r.getEsCorrecta());
                    return rResp;
                }).collect(Collectors.toList());

                pResp.setRespuestas(respuestasResp);
                return pResp;
            }).collect(Collectors.toList());

            sResp.setPreguntas(preguntasResp);
            return sResp;
        }).collect(Collectors.toList());

        response.setSecciones(seccionesResp);
        return response;
    }
    @Override
    public EncuestaCompletaResponse GetCompleta(Long idEncuesta) {
        return repository.getById(idEncuesta) != null
                ? mapToResponse(repository.getById(idEncuesta))
                : null;
    }
}