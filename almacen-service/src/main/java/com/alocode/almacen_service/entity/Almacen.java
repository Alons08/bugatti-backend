package com.alocode.almacen_service.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "almacen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlmacen;

    private Integer idUsuario;
    private String codigoAlmacen;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String region;
    private Integer capacidadMaxima;
    private String telefonoContacto;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
}
