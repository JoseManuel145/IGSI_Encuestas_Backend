package com.igsi.encuestas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoModel {
    private Long idDepartamento;
    private String nombre;
    private String descripcion;
    private Boolean deleted;
}
