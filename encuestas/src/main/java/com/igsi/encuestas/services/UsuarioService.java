package com.igsi.encuestas.services;

import com.igsi.encuestas.dto.usuarios.*;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    /**
     * Obtiene todos los usuarios registrados.
     */
    List<UsuarioDto> getAll();

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<UsuarioDto> getById(Long id);

    /**
     * Busca un usuario por su correo electrónico.
     * @param correo Correo del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<UsuarioDto> getByCorreo(String correo);

    /**
     * Guarda un nuevo usuario.
     * @param usuario Usuario a registrar
     * @return Usuario registrado con ID asignado
     */
    UsuarioDto save(UsuarioCreateDto usuario);

    /**
     * Actualiza los datos de un usuario existente.
     * @param id ID del usuario a actualizar
     * @param usuario Datos nuevos del usuario
     * @return true si la actualización fue exitosa, false si no
     */
    boolean update(Long id, UsuarioUpdateDto usuario);

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario
     * @return True si se eliminó, False si no
     */
    boolean delete(Long id);
    /**
     * Valida las credenciales del usuario.
     * @param usuarioLoginDto Datos para el incio de sesion
     * @return True si se eliminó, False si no
     */
    Optional<UsuarioLoginResponseDto> login(UsuarioLoginDto usuarioLoginDto);
}