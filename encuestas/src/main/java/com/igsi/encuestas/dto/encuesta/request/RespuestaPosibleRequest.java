package com.igsi.encuestas.dto.encuesta.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaPosibleRequest {
    private Long idPregunta;
    private String textoRespuesta;
    private Integer puntaje;
    private Boolean esCorrecta;
}
