package com.igsi.encuestas.dto;

import lombok.Data;

// Modelo para actualizar los campos del usuario
@Data
public class UsuarioUpdateDto {
    private String nombre;
    private String correo;
    private String password;
    private Long idDepartamento;
}
