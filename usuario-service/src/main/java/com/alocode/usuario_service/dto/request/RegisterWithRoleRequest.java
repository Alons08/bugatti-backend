package com.alocode.usuario_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWithRoleRequest {

    private String nombre;
    private String email;
    private String password;
    private Boolean active;
    private String imagenUrl;

    private String role; // role name to assign ("ADMIN" o "ALMACENERO")
}
