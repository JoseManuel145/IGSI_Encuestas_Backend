package com.igsi.encuestas.dto.usuarios.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginResponse {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol;
    private Long idDepartamento;
    private String token;
}
