package com.alocode.almacen_service.dto.request;

import lombok.Data;

@Data
public class MovimientoDetalleRequest {
    private Integer idMaterial;
    private Integer cantidad;
}
