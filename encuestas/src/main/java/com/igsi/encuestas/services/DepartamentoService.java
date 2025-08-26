package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.departamentos.request.DepartamentoRequest;
import com.igsi.encuestas.dto.departamentos.response.DepartamentoResponse;

import java.util.List;

public interface DepartamentoService {

    /**
     * Obtiene todos los departamentos registrados.
     * @return Lista de departamentos
     */
    List<DepartamentoResponse> getAll();

    /**
     * Busca un departamento por su ID.
     * @param id ID del departamento
     * @return Optional con el departamento si existe, vacío si no
     */
    DepartamentoResponse getById(Long id);

    /**
     * Guarda un nuevo departamento.
     * @param departamento Departamento a registrar
     * @return Departamento registrado con ID asignado
     */
    DepartamentoResponse save(DepartamentoRequest departamento);

    /**
     * Actualiza los datos de un departamento existente.
     * @param id ID del departamento a actualizar
     * @param departamento Datos nuevos del departamento
     * @return true si la actualización fue exitosa, false si no
     */
    boolean update(Long id, DepartamentoRequest departamento);

    /**
     * Elimina un departamento por su ID.
     * @param id ID del departamento
     */
    void delete(Long id);
    void softDelete(Long id);
}
