package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modelo de la respuesta posible (si es opcion multiple/cerrada)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaPosibleModel {
    private Long idRespuestaPosible;
    private Long idPregunta; // Respuesta enlazada a una pregunta
    private String textoRespuesta;
    private Integer puntaje;
    private Boolean esCorrecta;
}
