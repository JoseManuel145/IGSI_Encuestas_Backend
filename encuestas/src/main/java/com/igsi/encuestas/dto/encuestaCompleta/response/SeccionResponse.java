package com.igsi.encuestas.dto.encuestaCompleta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionResponse {
    private Long idSeccion;
    private String titulo;
    private String descripcion;
    private Integer orden;
    private List<PreguntaResponse> preguntas;
}