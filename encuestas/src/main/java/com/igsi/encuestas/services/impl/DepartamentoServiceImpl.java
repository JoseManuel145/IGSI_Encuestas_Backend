package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.departamentos.request.DepartamentoRequest;
import com.igsi.encuestas.dto.departamentos.response.DepartamentoResponse;
import com.igsi.encuestas.exceptions.BadRequestException;
import com.igsi.encuestas.exceptions.ResourceNotFoundException;
import com.igsi.encuestas.models.DepartamentoModel;
import com.igsi.encuestas.repositories.DepartamentoRepository;
import com.igsi.encuestas.services.DepartamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository repository;
    public DepartamentoServiceImpl(DepartamentoRepository repository) {
        this.repository = repository;
    }
    private DepartamentoResponse mapToResponse(DepartamentoModel departamento) {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setIdDepartamento(departamento.getIdDepartamento());
        response.setNombre(departamento.getNombre());
        response.setDescripcion(departamento.getDescripcion());
        return response;
    }
    @Override
    public List<DepartamentoResponse> getAll() {
        List<DepartamentoModel> departamentos = repository.getAll();
        if (departamentos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron departamentos");
        }
        return departamentos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public DepartamentoResponse getById(Long id) {
        DepartamentoModel depa = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento con id " + id + " no encontrado"));
        return mapToResponse(depa);
    }
    @Override
    public DepartamentoResponse save(DepartamentoRequest departamentoRequest) {
        if (departamentoRequest.getNombre() == null || departamentoRequest.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del departamento es obligatorio");
        }
        DepartamentoModel departamento = new DepartamentoModel(
                null,
                departamentoRequest.getNombre(),
                departamentoRequest.getDescripcion(),
                false
        );
        Long idGenerado = repository.save(departamento);
        departamento.setIdDepartamento(idGenerado);
        return mapToResponse(departamento);
    }
    @Override
    public boolean update(Long id, DepartamentoRequest departamentoRequest) {
        DepartamentoModel depa = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento con id " + id + " no encontrado"));
        depa.setNombre(departamentoRequest.getNombre());
        depa.setDescripcion(departamentoRequest.getDescripcion());
        return repository.update(depa) > 0;
    }
    @Override
    public void delete(Long id) {
        boolean deleted = repository.delete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Departamento con id " + id + " no encontrado o no pudo eliminarse");
        }
    }
    @Override
    public void softDelete(Long id) {
        boolean deleted = repository.softDelete(id) > 0;
        if (!deleted) {
            throw new ResourceNotFoundException("Departamento con id " + id + " no encontrado o no pudo deshabilitarse");
        }
    }
}