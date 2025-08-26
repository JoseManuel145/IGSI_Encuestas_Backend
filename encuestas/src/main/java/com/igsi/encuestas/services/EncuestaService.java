package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.encuesta.request.EncuestaRequest;
import com.igsi.encuestas.dto.encuesta.response.EncuestaResponse;

import java.util.List;

public interface EncuestaService {

    /**
     * Obtiene todas las encuestas registradas (ordenadas por fecha o id DESC).
     * @return Lista de encuestas
     */
    List<EncuestaResponse> getAll();

    /**
     * Busca una encuesta por su ID.
     * @param id ID de la encuesta
     * @return Optional con la encuesta si existe, vacío si no
     */
    EncuestaResponse getById(Long id);

    /**
     * Busca encuestas por ID de departamento.
     * @param idDepartamento ID del departamento
     * @return Lista de encuestas relacionadas al departamento
     */
    List<EncuestaResponse> getByDepartamento(Long idDepartamento);

    /**
     * Guarda una nueva encuesta.
     * @param encuesta Datos de la encuesta a registrar
     * @return Encuesta registrada con ID asignado
     */
    EncuestaResponse save(EncuestaRequest encuesta);

    /**
     * Actualiza los datos de una encuesta existente.
     * @param id ID de la encuesta a actualizar
     * @param encuesta Datos nuevos de la encuesta
     * @return true si la actualización fue exitosa, false si no
     */
    boolean update(Long id, EncuestaRequest encuesta);

    /**
     * Elimina una encuesta de forma permanente.
     * @param id ID de la encuesta
     * @return True si se eliminó, False si no
     */
    boolean delete(Long id);

    /**
     * Elimina una encuesta de forma lógica (soft delete).
     * @param id ID de la encuesta
     */
    void softDelete(Long id);
    void restaurar(Long id);
}
