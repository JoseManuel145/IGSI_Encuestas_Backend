package com.igsi.encuestas.dto.departamentos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoResponse {
    private Long idDepartamento;
    private String nombre;
    private String descripcion;
}
