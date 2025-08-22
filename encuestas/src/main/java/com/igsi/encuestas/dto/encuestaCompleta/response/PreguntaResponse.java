package com.igsi.encuestas.dto.encuestaCompleta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaResponse {
    private Long idPregunta;
    private String textoPregunta;
    private Long idTipoPregunta;
    private Integer orden;
    private String ayuda;
    private Integer puntaje;
    private List<RespuestaPosibleResponse> respuestas;
}
