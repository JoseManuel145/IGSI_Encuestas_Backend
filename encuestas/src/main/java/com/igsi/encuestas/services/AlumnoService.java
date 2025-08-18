package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.alumnos.AlumnoDto;
import com.igsi.encuestas.dto.alumnos.AlumnoLoginResponseDto;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {

    /**
     * Obtiene todos los alumnos registrados.
     * @return Lista de alumnos
     */
    List<AlumnoDto> getAll();

    /**
     * Busca un alumno por su ID.
     * @param id ID del alumno
     * @return Optional con el alumno si existe, vacío si no
     */
    Optional<AlumnoDto> getById(Long id);

    /**
     * Busca un alumno por su ID.
     * @param nombre ID del alumno
     * @return Optional con el alumno si existe, vacío si no
     */
    Optional<AlumnoDto> getByNombre(String nombre);

    /**
     * Guarda un nuevo alumno.
     * @param alumno Alumno a registrar
     * @return Alumno registrado con ID asignado
     */
    AlumnoDto save(AlumnoDto alumno);

    /**
     * Elimina un alumno por su ID.
     * @param id ID del alumno
     * @return True si se eliminó, False si no
     */
    boolean delete(Long id);

    /**
     * Valida el login de un alumno con nombre y contraseña.
     * @param nombre Nombre del alumno
     * @param password Contraseña del alumno
     * @return Optional con el alumno si las credenciales son correctas
     */
    Optional<AlumnoLoginResponseDto> login(String nombre, String password);
}
