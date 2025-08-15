package com.igsi.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioLoginResponseDto {
    private String correo;
    private String rol;
    private String token;
}