package com.igsi.encuestas.dto.encuesta.request;

import lombok.Data;

@Data
public class PreguntaRequest {
    private Long idSeccion;
    private String textoPregunta;
    private Long idTipoPregunta;
    private Integer orden;
    private String ayuda;
    private Integer puntaje;
}
