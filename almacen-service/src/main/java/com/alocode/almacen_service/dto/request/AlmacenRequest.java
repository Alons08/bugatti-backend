package com.alocode.almacen_service.dto.request;

import lombok.Data;

@Data
public class AlmacenRequest {
    private Integer idUsuario;
    private String codigoAlmacen;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String region;
    private Integer capacidadMaxima;
    private String telefonoContacto;
}
