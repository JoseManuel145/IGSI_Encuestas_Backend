package com.igsi.encuestas.dto.alumnos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoLoginResponse {
    private Long idAlumno;
    private String nombre;
    private String token;
}
