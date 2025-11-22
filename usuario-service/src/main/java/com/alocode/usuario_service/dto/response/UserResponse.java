package com.alocode.usuario_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String nombre;
    private String email;
    private Boolean active;
    private String imagenUrl;
    private java.time.LocalDateTime fechaCreacion;
    private Set<String> roles;
}
