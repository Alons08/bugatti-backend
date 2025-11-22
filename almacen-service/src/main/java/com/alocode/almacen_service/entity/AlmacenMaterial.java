package com.alocode.almacen_service.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "almacen_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AlmacenMaterialId.class)
public class AlmacenMaterial {
    @Id
    private Integer idAlmacen;
    @Id
    private Integer idMaterial;
    private Integer stockActual;
    private Integer stockMinimo;
}
