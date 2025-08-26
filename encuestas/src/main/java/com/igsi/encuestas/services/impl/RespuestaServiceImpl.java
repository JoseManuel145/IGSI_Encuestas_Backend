package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.respuestas.request.RespuestaRequest;
import com.igsi.encuestas.dto.respuestas.response.RespuestaResponse;
import com.igsi.encuestas.models.RespuestaModel;
import com.igsi.encuestas.repositories.RespuestaRepository;
import com.igsi.encuestas.services.RespuestaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    private final RespuestaRepository repository;
    public RespuestaServiceImpl(RespuestaRepository repository) {
        this.repository = repository;
    }
    private RespuestaResponse mapToResponse(RespuestaModel model) {
        RespuestaResponse response = new RespuestaResponse();
        response.setId_respuesta(model.getId_respuesta());
        response.setId_alumno(model.getId_alumno());
        response.setId_pregunta(model.getId_pregunta());
        response.setId_respuesta(model.getId_respuesta_posible());
        response.setRespuesta_abierta(model.getRespuesta_abierta());
        return response;
    }
    @Override
    public RespuestaResponse save(RespuestaRequest request) {
        RespuestaModel model = new RespuestaModel(
                null,
                request.getId_alumno(),
                request.getId_pregunta(),
                request.getId_respuesta_posible(),
                request.getRespuesta_abierta()
        );
        Long idGenerado = repository.save(model);
        model.setId_respuesta(idGenerado);
        return mapToResponse(model);
    }
    @Override
    public List<Map<String, Object>> getAll(Long idAlumno, Long idEncuesta) {
        return repository.getByEncuesta(idAlumno, idEncuesta);
    }
    @Override
    public boolean update(Long id, RespuestaRequest request) {
        Optional<RespuestaModel> existente = repository.getById(id);
        if (existente.isEmpty()) return false;

        RespuestaModel model = existente.get();
        model.setId_alumno(request.getId_alumno());
        model.setId_pregunta(request.getId_pregunta());
        model.setId_respuesta_posible(request.getId_respuesta_posible());
        model.setRespuesta_abierta(request.getRespuesta_abierta());
        return repository.update(id, model) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
}