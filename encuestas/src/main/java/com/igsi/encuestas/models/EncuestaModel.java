package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Modelo para las encuestas
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncuestaModel {
    private Long idEncuesta;
    private String titulo;
    private String descripcion;
    private Long idDepartamento; // Encuesta enlazada a un dpto
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // habilitada, deshabilitada, cerrada
    private Boolean deleted; // borrado logico por si "deshace" la opcion de eliminar

}