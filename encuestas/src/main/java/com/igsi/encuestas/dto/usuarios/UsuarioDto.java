package com.igsi.encuestas.dto.usuarios;

import lombok.Data;
// Modelo para listar todos los usuarios
@Data
public class UsuarioDto {
    private String nombre;
    private String correo;
    private Long idDepartamento;
}