package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modelo para manejar los tipos de pregunta
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPreguntaModel {
    private Long idTipo;
    private String nombre; // 'abierta', 'opcion_multiple', 'escala_likert', 'si_no'
    private String descripcion;
}
