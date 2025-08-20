package com.igsi.encuestas.dto.alumnos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoRequest {
    private String nombre;
    private String password;
}
