package com.igsi.encuestas.dto.encuesta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncuestaResponse {
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Long idDepartamento; // Encuesta enlazada a un dpto
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // habilitada, deshabilitada, cerrada
    private Boolean deleted; // borrado logico por si "deshace" la opcion de eliminar
}
