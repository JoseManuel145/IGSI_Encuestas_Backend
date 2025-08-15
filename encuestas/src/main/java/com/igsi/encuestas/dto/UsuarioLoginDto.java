package com.igsi.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Modelo para iniciar sesion
public class UsuarioLoginDto {
    private String correo;
    private String password;
}
