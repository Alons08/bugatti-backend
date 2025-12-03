package com.alocode.usuario_service.controller;

import com.alocode.usuario_service.dto.request.LoginRequest;
import com.alocode.usuario_service.dto.request.RegisterRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.service.AuthService;
import com.alocode.usuario_service.dto.request.ForgotPasswordRequest;
import com.alocode.usuario_service.dto.request.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import com.alocode.usuario_service.dto.response.UserResponse;
import com.alocode.usuario_service.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth") // Ruta pública
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AdminService adminService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.sendPasswordResetCode(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getEmail(), request.getCode(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    // Endpoint privado para obtener el usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        // Se asume que el principal es el email
        UserResponse user = adminService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

}
