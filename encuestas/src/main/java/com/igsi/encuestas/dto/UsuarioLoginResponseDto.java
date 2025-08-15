package com.igsi.encuestas.dto;

public class UsuarioLoginResponseDto {
    private String correo;
    private String rol;
    private String token;

    public UsuarioLoginResponseDto(String correo, String rol, String token) {
        this.correo = correo;
        this.rol = rol;
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}