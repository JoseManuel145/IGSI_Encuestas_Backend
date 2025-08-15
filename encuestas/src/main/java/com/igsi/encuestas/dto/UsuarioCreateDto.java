package com.igsi.encuestas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Modelo para registrar nuevos usuarios
public class UsuarioCreateDto {
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Long idDepartamento;
}
