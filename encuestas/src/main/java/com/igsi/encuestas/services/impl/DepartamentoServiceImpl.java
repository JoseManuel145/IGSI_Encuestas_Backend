package com.igsi.encuestas.services.impl;

import com.igsi.encuestas.dto.departamentos.DepartamentoDto;
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
//  Mapeo de DepartamentoModel a DepartamentoDto
    private DepartamentoDto mapToDto(DepartamentoModel departamento) {
        DepartamentoDto dto = new DepartamentoDto();
        dto.setNombre(departamento.getNombre());
        dto.setDescripcion(departamento.getDescripcion());
        dto.setDeleted(departamento.getDeleted());
        return dto;
    }
    @Override
    public List<DepartamentoDto> getAll() {
        return repository.getAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<DepartamentoDto> getById(Long id) {
        return repository.getById(id)
                .map(this::mapToDto);
    }
    @Override
    public DepartamentoDto save(DepartamentoDto departamentoDto) {
        // Convertimos DTO a Model
        DepartamentoModel departamento = new DepartamentoModel(
                null,
                departamentoDto.getNombre(),
                departamentoDto.getDescripcion(),
                false
        );

        // Guardamos en la base de datos
        repository.saveDepartamento(departamento);

        // Convertimos nuevamente a DTO y retornamos
        return mapToDto(departamento);
    }
    @Override
    public boolean update(Long id, DepartamentoDto departamento) {
        Optional<DepartamentoModel> existing = repository.getById(id);

        if(existing.isEmpty())return false;

        DepartamentoModel depa = existing.get();

        depa.setNombre(departamento.getNombre());
        depa.setDescripcion(departamento.getDescripcion());
        depa.setDeleted(departamento.getDeleted());

        return repository.updateDepartamento(depa) > 0;
    }
    @Override
    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
}
