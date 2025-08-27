package com.igsi.encuestas.dto.respuestas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaEstadisticaResponse {
    private Long idRespuestaPosible;
    private String textoRespuesta;
    private Long totalSeleccionado;
}
