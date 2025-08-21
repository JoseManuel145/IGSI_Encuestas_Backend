package com.igsi.encuestas.dto.encuesta.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPreguntaRequest {
    private String nombre;
    private String descripcion;
}
