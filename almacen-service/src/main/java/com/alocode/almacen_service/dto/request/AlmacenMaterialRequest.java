package com.alocode.almacen_service.dto.request;

import lombok.Data;

@Data
public class AlmacenMaterialRequest {
    private Integer idAlmacen;
    private Integer idMaterial;
    private Integer stockActual;
    private Integer stockMinimo;
}
