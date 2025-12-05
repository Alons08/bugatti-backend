package com.alocode.almacen_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasResponse {
    private Integer stockTotal;
    private Integer materialesAgotados;
    private Integer movimientosHoy;
}