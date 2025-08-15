package com.igsi.encuestas.dto;

import lombok.Data;

@Data
// Modelo para listar todos los usuarios
public class UsuarioDto {
    private String nombre;
    private String correo;
    private Long idDepartamento;
}