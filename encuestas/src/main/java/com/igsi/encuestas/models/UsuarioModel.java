package com.igsi.encuestas.models;

import lombok.Data;

@Data

public class UsuarioModel {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Long idDepartamento;

    public UsuarioModel() {
    }

    public UsuarioModel(Long idUsuario, String nombre, String correo, String password, String rol, Long idDepartamento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.idDepartamento = idDepartamento;
    }
}

