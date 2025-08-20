package com.igsi.encuestas.dto.usuarios.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginRequest {
    private String correo;
    private String password;
}
