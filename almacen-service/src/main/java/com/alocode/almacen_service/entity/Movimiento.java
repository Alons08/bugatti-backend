package com.alocode.almacen_service.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimiento;
    private Integer idAlmacen;
    private Integer idUsuario;
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaHora;
    private String descripcion;
    private String referencia;
}
