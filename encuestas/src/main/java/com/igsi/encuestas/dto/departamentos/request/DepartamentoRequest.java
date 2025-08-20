package com.igsi.encuestas.dto.departamentos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoRequest {
    private String nombre;
    private String descripcion;
}
