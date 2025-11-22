package com.alocode.usuario_service.service;

import com.alocode.usuario_service.dto.request.LoginRequest;
import com.alocode.usuario_service.dto.request.RegisterRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);

    void sendPasswordResetCode(String email);
    void resetPassword(String email, String code, String newPassword);

}