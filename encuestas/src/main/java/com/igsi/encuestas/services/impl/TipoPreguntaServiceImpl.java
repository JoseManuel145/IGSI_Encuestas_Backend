package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.encuesta.request.TipoPreguntaRequest;
import com.igsi.encuestas.dto.encuesta.response.TipoPreguntaResponse;
import com.igsi.encuestas.models.TipoPreguntaModel;
import com.igsi.encuestas.repositories.TipoPreguntaRepository;
import com.igsi.encuestas.services.TipoPreguntaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoPreguntaServiceImpl implements TipoPreguntaService {
    private final TipoPreguntaRepository repository;
    public TipoPreguntaServiceImpl(TipoPreguntaRepository tipoPreguntaRepository) {
        this.repository = tipoPreguntaRepository;
    }
//  Mapeo de la respuesta
    private TipoPreguntaResponse mapToResponse(TipoPreguntaModel tipoPreguntaModel) {
        TipoPreguntaResponse response = new TipoPreguntaResponse();
        response.setIdTipo(tipoPreguntaModel.getIdTipo());
        response.setNombre(tipoPreguntaModel.getNombre());
        response.setDescripcion(tipoPreguntaModel.getDescripcion());
        return response;
    }
    @Override
    public List<TipoPreguntaResponse> getAll() {
        return repository.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<TipoPreguntaResponse> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToResponse);
    }
    @Override
    public TipoPreguntaResponse save(TipoPreguntaRequest tipoPregunta) {
        TipoPreguntaModel model = new TipoPreguntaModel(
                null,
                tipoPregunta.getNombre(),
                tipoPregunta.getDescripcion()
        );
        Long id = repository.save(model);
        model.setIdTipo(id);
        return mapToResponse(model);
    }
    @Override
    public boolean update(Long id, TipoPreguntaRequest tipoPregunta) {
        Optional<TipoPreguntaModel> existing = repository.getById(id);
        if (existing.isEmpty()) return false;

        TipoPreguntaModel model = existing.get();
        model.setNombre(tipoPregunta.getNombre());
        model.setDescripcion(tipoPregunta.getDescripcion());
        return repository.update(model) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
}