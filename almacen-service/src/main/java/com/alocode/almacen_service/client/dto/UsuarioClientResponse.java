package com.alocode.almacen_service.client.dto;

import lombok.Data;

@Data
public class UsuarioClientResponse {
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String imagenUrl;
    private Boolean estado;
}
