package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.departamentos.DepartamentoDto;

import java.util.List;
import java.util.Optional;

public interface DepartamentoService {

    /**
     * Obtiene todos los departamentos registrados.
     * @return Lista de departamentos
     */
    List<DepartamentoDto> getAll();

    /**
     * Busca un departamento por su ID.
     * @param id ID del departamento
     * @return Optional con el departamento si existe, vacío si no
     */
    Optional<DepartamentoDto> getById(Long id);

    /**
     * Guarda un nuevo departamento.
     * @param departamento Departamento a registrar
     * @return Departamento registrado con ID asignado
     */
    DepartamentoDto save(DepartamentoDto departamento);

    /**
     * Actualiza los datos de un departamento existente.
     * @param id ID del departamento a actualizar
     * @param departamento Datos nuevos del departamento
     * @return true si la actualización fue exitosa, false si no
     */
    boolean update(Long id, DepartamentoDto departamento);

    /**
     * Elimina un departamento por su ID.
     * @param id ID del departamento
     * @return True si se eliminó, False si no
     */
    boolean delete(Long id);
}
