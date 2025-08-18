package com.igsi.encuestas.dto.alumnos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoLoginResponseDto {
    private String nombre;
    private String token;

}
