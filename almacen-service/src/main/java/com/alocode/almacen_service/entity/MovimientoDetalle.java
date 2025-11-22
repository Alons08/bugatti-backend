package com.alocode.almacen_service.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "movimiento_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;
    private Integer idMovimiento;
    private Integer idMaterial;
    private Integer cantidad;
}
