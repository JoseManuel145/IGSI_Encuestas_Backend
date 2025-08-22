package com.igsi.encuestas.dto.encuesta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaResponse {
    private Long idPregunta;
    private Long idSeccion;
    private String textoPregunta;
    private Long idTipoPregunta;
    private Integer orden;
    private String ayuda;
    private Integer puntaje;
}
