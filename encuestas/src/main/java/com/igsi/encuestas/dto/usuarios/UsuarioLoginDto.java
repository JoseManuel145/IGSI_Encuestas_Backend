package com.igsi.encuestas.dto.usuarios;

import lombok.Data;

// Modelo para iniciar sesion
@Data
public class UsuarioLoginDto {
    private String correo;
    private String password;

}
