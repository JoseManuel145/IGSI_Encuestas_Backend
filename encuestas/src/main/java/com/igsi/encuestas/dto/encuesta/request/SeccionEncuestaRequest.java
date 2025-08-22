package com.igsi.encuestas.dto.encuesta.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionEncuestaRequest {
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Integer orden; // para mantener las secciones ordenadas
}
