package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Modelo para las secciones de la encuesta
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionEncuestaModel {
    private Long idSeccion;
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Integer orden; // para mantener las secciones ordenadas
}
