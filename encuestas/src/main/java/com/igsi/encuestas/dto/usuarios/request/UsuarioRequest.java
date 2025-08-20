package com.igsi.encuestas.dto.usuarios.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Long idDepartamento;
}