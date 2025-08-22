package com.igsi.encuestas.dto.encuesta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaPosibleResponse {
    private Long idRespuestaPosible;
    private Long idPregunta;
    private String textoRespuesta;
    private Integer puntaje;
    private Boolean esCorrecta;
}
