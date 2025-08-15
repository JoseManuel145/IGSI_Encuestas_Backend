package com.igsi.encuestas.dto.usuarios;

import lombok.Data;
// Modelo para registrar nuevos usuarios
@Data
public class UsuarioCreateDto {
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Long idDepartamento;

}
