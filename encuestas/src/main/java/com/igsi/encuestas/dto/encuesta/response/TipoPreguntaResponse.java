package com.igsi.encuestas.dto.encuesta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPreguntaResponse {
    private Long idTipo;
    private String nombre;
    private String descripcion;
}
