package com.alocode.almacen_service.dto.response;

import lombok.Data;

@Data
public class MovimientoDetalleResponse {
    private Integer idDetalle;
    private Integer idMaterial;
    private Integer cantidad;
}
