package com.alocode.usuario_service.config;

import com.alocode.usuario_service.entity.RoleEnum;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


// Clase para inicializar datos en la base de datos al ejecutar la aplicación
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed roles desde RoleEnum
        for (RoleEnum r : RoleEnum.values()) {
            String roleName = r.name();
            roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(Role.builder().name(roleName).description(roleName + " role").build()));
        }
    }

    // Método eliminado: createCountryIfNotExists
}
