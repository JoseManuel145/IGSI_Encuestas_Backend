package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.departamentos.request.DepartamentoRequest;
import com.igsi.encuestas.dto.departamentos.response.DepartamentoResponse;
import com.igsi.encuestas.models.DepartamentoModel;
import com.igsi.encuestas.repositories.DepartamentoRepository;
import com.igsi.encuestas.services.DepartamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository repository;

    public DepartamentoServiceImpl(DepartamentoRepository repository) {
        this.repository = repository;
    }

    // Mapea DepartamentoModel a DepartamentoResponse
    private DepartamentoResponse mapToResponse(DepartamentoModel departamento) {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setIdDepartamento(departamento.getIdDepartamento());
        response.setNombre(departamento.getNombre());
        response.setDescripcion(departamento.getDescripcion());
        return response;
    }

    @Override
    public List<DepartamentoResponse> getAll() {
        return repository.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartamentoResponse> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToResponse);
    }

    @Override
    public DepartamentoResponse save(DepartamentoRequest departamentoRequest) {
        // Convertir request a model
        DepartamentoModel departamento = new DepartamentoModel(
                null,
                departamentoRequest.getNombre(),
                departamentoRequest.getDescripcion(),
                false
        );

        // Guardar en base de datos
        Long idGenerado = repository.saveDepartamento(departamento);
        departamento.setIdDepartamento(idGenerado);
        // Retornar como response
        return mapToResponse(departamento);
    }

    @Override
    public boolean update(Long id, DepartamentoRequest departamentoRequest) {
        Optional<DepartamentoModel> existing = repository.getById(id);
        if (existing.isEmpty()) return false;

        DepartamentoModel depa = existing.get();
        depa.setNombre(departamentoRequest.getNombre());
        depa.setDescripcion(departamentoRequest.getDescripcion());

        return repository.updateDepartamento(depa) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }

    @Override
    public boolean softDelete(Long id) {
        return repository.softDelete(id) > 0;
    }
}
