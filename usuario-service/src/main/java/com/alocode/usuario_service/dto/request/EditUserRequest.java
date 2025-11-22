package com.alocode.usuario_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    private String nombre;
    private String email;
    private String password;
    private Boolean active; // Keeping this field as it is part of the User model
    private String imagenUrl; // Keeping this field as it is part of the User model
    private String role; // nombre del rol principal
}
