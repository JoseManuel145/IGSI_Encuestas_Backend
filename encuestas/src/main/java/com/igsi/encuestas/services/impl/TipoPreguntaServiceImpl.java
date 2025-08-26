package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.TipoPreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.TipoPreguntaResponse;
import com.igsi.encuestas.exceptions.BadRequestException;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.TipoPreguntaModel;
import com.igsi.encuestas.repositories.TipoPreguntaRepository;
import com.igsi.encuestas.services.TipoPreguntaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoPreguntaServiceImpl implements TipoPreguntaService {
    private final TipoPreguntaRepository repository;
    public TipoPreguntaServiceImpl(TipoPreguntaRepository tipoPreguntaRepository) {
        this.repository = tipoPreguntaRepository;
    }
    private TipoPreguntaResponse mapToResponse(TipoPreguntaModel model) {
        TipoPreguntaResponse response = new TipoPreguntaResponse();
        response.setIdTipo(model.getIdTipo());
        response.setNombre(model.getNombre());
        response.setDescripcion(model.getDescripcion());
        return response;
    }
    @Override
    public List<TipoPreguntaResponse> getAll() {
        List<TipoPreguntaModel> tipos = repository.getAll();
        if (tipos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron tipos de pregunta");
        }
        return tipos.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public TipoPreguntaResponse getById(Long id) {
        TipoPreguntaModel model = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de pregunta con id " + id + " no encontrado"));
        return mapToResponse(model);
    }
    @Override
    public TipoPreguntaResponse save(TipoPreguntaRequest request) {
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del tipo de pregunta es obligatorio");
        }
        TipoPreguntaModel model = new TipoPreguntaModel(null, request.getNombre(), request.getDescripcion());
        Long id = repository.save(model);
        model.setIdTipo(id);
        return mapToResponse(model);
    }
    @Override
    public boolean update(Long id, TipoPreguntaRequest request) {
        TipoPreguntaModel model = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de pregunta con id " + id + " no encontrado"));

        model.setNombre(request.getNombre());
        model.setDescripcion(request.getDescripcion());

        boolean updated = repository.update(model) > 0;
        if (!updated) {
            throw new ResourceNotFoundException("No se pudo actualizar el tipo de pregunta con id " + id);
        }
        return true;
    }
    @Override
    public boolean delete(Long id) {
        boolean deleted = repository.delete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Tipo de pregunta con id " + id + " no encontrado o no pudo eliminarse");
        }
        return true;
    }
}