package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.alumnos.request.AlumnoRequest;
import com.igsi.encuestas.dto.alumnos.response.AlumnoIdResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoLoginResponse;
import com.igsi.encuestas.dto.alumnos.response.AlumnoResponse;

import java.util.List;

public interface AlumnoService {

    /**
     * Obtiene todos los alumnos registrados.
     * @return Lista de alumnos
     */
    List<AlumnoResponse> getAll();

    /**
     * Busca un alumno por su ID.
     * @param id ID del alumno
     * @return Optional con el alumno si existe, vacío si no
     */
    AlumnoIdResponse getById(Long id);

    /**
     * Busca un alumno por su ID.
     * @param nombre ID del alumno
     * @return Optional con el alumno si existe, vacío si no
     */
    AlumnoResponse getByNombre(String nombre);

    /**
     * Guarda un nuevo alumno.
     * @param alumno Alumno a registrar
     * @return Alumno registrado con ID asignado
     */
    AlumnoResponse save(AlumnoRequest alumno);

    /**
     * Elimina un alumno por su ID.
     * @param id ID del alumno
     * @return True si se eliminó, False si no
     */
    boolean delete(Long id);

    /**
     * Valida el login de un alumno con nombre y contraseña.
     * @param alumnoDto Esquema del alumno para hacer login
     * @return Optional con el alumno si las credenciales son correctas
     */
    AlumnoLoginResponse login(AlumnoRequest alumnoDto);
}
