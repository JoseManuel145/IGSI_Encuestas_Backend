package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modelo de la pregunta que tendra la encuesta
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaModel {
    private Long idPregunta;
    private Long idSeccion; // Enlazada a una seccion
    private String textoPregunta;
    private Long idTipoPregunta;
    private Integer orden; // Para ir determinando en que orden mostrar las preguntas
    private String ayuda; // Tip por si no sabe responder la pregunta
    private Integer puntaje;
}
