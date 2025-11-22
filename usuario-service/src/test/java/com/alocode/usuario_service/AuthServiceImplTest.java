package com.alocode.usuario_service;

import com.alocode.usuario_service.dto.request.LoginRequest;
import com.alocode.usuario_service.dto.request.RegisterRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.entity.User;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.repository.UserRepository;
import com.alocode.usuario_service.repository.RoleRepository;
import com.alocode.usuario_service.security.JwtService;
import com.alocode.usuario_service.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthServiceImpl authService;

    public AuthServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginReturnsToken() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@mail.com");
        req.setPassword("1234");
        User user = User.builder().email("test@mail.com").password("1234").build();
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn("token123");
        AuthResponse resp = authService.login(req);
        assertEquals("token123", resp.getToken());
    }

    @Test
    void testRegisterReturnsToken() {
        RegisterRequest req = new RegisterRequest();
        req.setNombre("Juan");
        req.setEmail("juan@mail.com");
        req.setPassword("abcd");
        req.setActive(true);
        req.setImagenUrl("img.jpg");
        Role role = Role.builder().name("ALMACENERO").description("Default almacenero role").build();
        when(roleRepository.findByName("ALMACENERO")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("abcd")).thenReturn("hashed");
        User user = User.builder().nombre("Juan").email("juan@mail.com").password("hashed").active(true).imagenUrl("img.jpg").build();
        when(jwtService.getToken(any(User.class))).thenReturn("token456");
        AuthResponse resp = authService.register(req);
        assertEquals("token456", resp.getToken());
    }
}
