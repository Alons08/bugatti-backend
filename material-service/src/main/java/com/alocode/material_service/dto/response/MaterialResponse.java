package com.alocode.material_service.dto.response;

import lombok.Data;

@Data
public class MaterialResponse {
    private Integer idMaterial;
    private Integer idTipo;
    private String nombreTipo;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private String unidadMedida;
}
