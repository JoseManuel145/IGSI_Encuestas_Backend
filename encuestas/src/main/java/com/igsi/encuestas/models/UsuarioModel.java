package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Long idDepartamento;
}

