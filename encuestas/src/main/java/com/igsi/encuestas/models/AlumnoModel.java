package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoModel {
    private Long idAlumno;
    private String nombre;
    private String password;
}
