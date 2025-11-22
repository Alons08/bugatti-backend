package com.alocode.usuario_service.service.impl;

import com.alocode.usuario_service.dto.request.LoginRequest;
import com.alocode.usuario_service.dto.request.RegisterRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.entity.User;
import com.alocode.usuario_service.entity.PasswordResetCode;
import com.alocode.usuario_service.repository.PasswordResetCodeRepository;
import com.alocode.usuario_service.service.EmailService;
import java.time.LocalDateTime;
import java.util.Random;
import com.alocode.usuario_service.repository.UserRepository;
import com.alocode.usuario_service.repository.RoleRepository;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.security.JwtService;
import com.alocode.usuario_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final PasswordResetCodeRepository passwordResetCodeRepository;
        private final EmailService emailService;
        @Override
        public void sendPasswordResetCode(String email) {
                // Generar código de 6 dígitos
                String code = String.format("%06d", new Random().nextInt(1000000));
                // Eliminar códigos previos
                passwordResetCodeRepository.deleteByEmail(email);
                // Guardar nuevo código con expiración (15 min)
                PasswordResetCode resetCode = PasswordResetCode.builder()
                        .email(email)
                        .code(code)
                        .expiresAt(LocalDateTime.now().plusMinutes(15))
                        .build();
                passwordResetCodeRepository.save(resetCode);
                // Enviar email
                emailService.sendEmail(email, "Código de recuperación", "Tu código de recuperación es: " + code);
        }

        @Override
        @Transactional
        public void resetPassword(String email, String code, String newPassword) {
                PasswordResetCode resetCode = passwordResetCodeRepository.findByEmailAndCode(email, code)
                        .orElseThrow(() -> new IllegalArgumentException("Código inválido"));
                if (resetCode.getExpiresAt().isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException("El código ha expirado");
                }
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                passwordResetCodeRepository.deleteByEmail(email);
        }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.getActive() == null ? Boolean.TRUE : request.getActive())
                .imagenUrl(request.getImagenUrl())
                .fechaCreacion(java.time.LocalDateTime.now())
                .build();

        Role almacenero = roleRepository.findByName("ALMACENERO")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ALMACENERO").description("Default almacenero role").build()));

        user.setRoles(new java.util.HashSet<>());
        user.getRoles().add(almacenero);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}