package com.igsi.encuestas.dto.respuestas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaResponse {
    private Long id_respuesta; //PK
    private Long id_alumno; // RESPUESTA RELACIONADA A UN ALUMNO
    private Long id_pregunta; // RESPUESTA RELACIONADA A LA PREGUNTA
    private Long id_respuesta_posible; // que respuesta eligio / NULL si es respuesta abierta
    private String respuesta_abierta; // SI 'id_respuesta_posible' es NULL aca ira el resultado
    private LocalDate fecha_respuesta;
}
