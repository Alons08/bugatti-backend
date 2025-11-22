package com.alocode.usuario_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.alocode.usuario_service.repository.UserRepository;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1") //Ruta protegida (para pruebas)
@RequiredArgsConstructor
public class DemoController {

    private final UserRepository userRepository;

    @PostMapping(value = "demo")
    public String welcome(Authentication authentication){
        String username = authentication == null ? "anonymous" : authentication.getName();

        var userOpt = userRepository.findByEmail(username);

        String nombre = userOpt.map(u -> u.getNombre()).orElse(username);
        String roles = userOpt.map(u -> u.getRoles() == null ? "" : u.getRoles().stream().map(r -> r.getName()).collect(Collectors.joining(", "))).orElse("");

        return "Welcome " + nombre + "! Your roles: " + (roles.isEmpty() ? "none" : roles);
    }

}
