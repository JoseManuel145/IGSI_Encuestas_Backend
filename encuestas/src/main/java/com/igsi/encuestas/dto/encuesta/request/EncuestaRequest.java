package com.igsi.encuestas.dto.encuesta.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncuestaRequest {
    private String titulo;
    private String descripcion;
    private Long idDepartamento; // Encuesta enlazada a un dpto
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // habilitada, deshabilitada, cerrada
}
