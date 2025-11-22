package com.alocode.almacen_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovimientoResponse {
    private Integer idMovimiento;
    private Integer idAlmacen;
    private Integer idUsuario;
    private String tipoMovimiento;
    private LocalDateTime fechaHora;
    private String descripcion;
    private String referencia;
    private List<MovimientoDetalleResponse> detalles;
}
