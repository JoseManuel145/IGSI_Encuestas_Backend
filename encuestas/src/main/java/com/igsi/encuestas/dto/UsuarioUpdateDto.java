package com.igsi.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Modelo para actualizar los campos del usuario
public class UsuarioUpdateDto {
    private String nombre;
    private String correo;
    private String password;
    private Long idDepartamento;
}
