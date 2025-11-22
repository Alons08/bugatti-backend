package com.alocode.almacen_service.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenMaterialId implements Serializable {
    private Integer idAlmacen;
    private Integer idMaterial;
}
