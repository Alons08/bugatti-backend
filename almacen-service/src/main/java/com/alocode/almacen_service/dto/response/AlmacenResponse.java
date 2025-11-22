package com.alocode.almacen_service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlmacenResponse {
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
    private String nombreUsuario;
}
