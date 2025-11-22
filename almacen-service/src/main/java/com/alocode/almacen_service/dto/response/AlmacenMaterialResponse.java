package com.alocode.almacen_service.dto.response;

import lombok.Data;

@Data
public class AlmacenMaterialResponse {
    private Integer idAlmacen;
    private Integer idMaterial;
    private Integer stockActual;
    private Integer stockMinimo;
}
