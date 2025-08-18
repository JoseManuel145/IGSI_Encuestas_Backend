package com.igsi.encuestas.dto.departamentos;

import lombok.Data;

@Data
// Modelo para manipular los departamentos
public class DepartamentoDto {
    private String nombre;
    private String descripcion;
    private Boolean deleted;
}
