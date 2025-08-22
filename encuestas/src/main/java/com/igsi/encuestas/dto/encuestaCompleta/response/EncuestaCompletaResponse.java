package com.igsi.encuestas.dto.encuestaCompleta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncuestaCompletaResponse {
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Long idDepartamento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Boolean deleted;
    private List<SeccionResponse> secciones;
}