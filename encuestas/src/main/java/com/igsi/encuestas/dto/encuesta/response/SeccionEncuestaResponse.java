package com.igsi.encuestas.dto.encuesta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionEncuestaResponse {
    private Long idSeccion;
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Integer orden; // para mantener las secciones ordenadas
}
