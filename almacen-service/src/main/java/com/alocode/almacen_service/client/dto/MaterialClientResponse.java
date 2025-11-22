package com.alocode.almacen_service.client.dto;

import lombok.Data;

@Data
public class MaterialClientResponse {
    private Integer idMaterial;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private String unidadMedida;
}
