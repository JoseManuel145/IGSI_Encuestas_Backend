package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.RespuestaPosibleRequest;
import com.igsi.encuestas.dto.encuesta.response.RespuestaPosibleResponse;
import com.igsi.encuestas.models.RespuestaPosibleModel;
import com.igsi.encuestas.repositories.RespuestaPosibleRepository;
import com.igsi.encuestas.services.RespuestaPosibleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaPosibleServiceImpl implements RespuestaPosibleService {

    private final RespuestaPosibleRepository repository;
    public RespuestaPosibleServiceImpl(RespuestaPosibleRepository repository) {
        this.repository = repository;
    }
    private RespuestaPosibleResponse mapToResponse(RespuestaPosibleModel model) {
        RespuestaPosibleResponse response = new RespuestaPosibleResponse();
        response.setIdRespuestaPosible(model.getIdRespuestaPosible());
        response.setIdPregunta(model.getIdPregunta());
        response.setTextoRespuesta(model.getTextoRespuesta());
        response.setPuntaje(model.getPuntaje());
        response.setEsCorrecta(model.getEsCorrecta());
        return response;
    }
    @Override
    public List<RespuestaPosibleResponse> getAll(Long idPregunta) {
        return repository.getAll(idPregunta)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public RespuestaPosibleResponse save(Long idPregunta, RespuestaPosibleRequest request) {
        // Convertir Request a Model
        RespuestaPosibleModel model = new RespuestaPosibleModel(
                null,
                idPregunta,
                request.getTextoRespuesta(),
                request.getPuntaje(),
                request.getEsCorrecta()
        );
        Long idGenerado = repository.save(model);
        model.setIdRespuestaPosible(idGenerado);

        return mapToResponse(model);
    }
    @Override
    public boolean update(Long idPregunta, Long idRespuesta, RespuestaPosibleRequest request) {
        RespuestaPosibleModel model = new RespuestaPosibleModel(
                null,
                request.getIdPregunta(),
                request.getTextoRespuesta(),
                request.getPuntaje(),
                request.getEsCorrecta()
        );
        return repository.update(idRespuesta, model) > 0;
    }
    @Override
    public boolean delete(Long idPregunta, Long idRespuesta) {
        return repository.delete(idRespuesta) > 0;
    }
}