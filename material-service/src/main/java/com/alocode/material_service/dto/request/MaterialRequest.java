package com.alocode.material_service.dto.request;

import lombok.Data;

@Data
public class MaterialRequest {
    private Integer idTipo;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private String unidadMedida;
}
