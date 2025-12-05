package com.alocode.almacen_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoRecienteResponse {
    private String nombreMaterial;
    private String tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime hora;
}