package com.alocode.usuario_service.service.impl;

import com.alocode.usuario_service.dto.request.RegisterWithRoleRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.entity.User;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.dto.request.EditUserRequest;
import com.alocode.usuario_service.repository.UserRepository;
import com.alocode.usuario_service.security.JwtService;
import com.alocode.usuario_service.repository.RoleRepository;
import com.alocode.usuario_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.alocode.usuario_service.dto.response.UserResponse;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerWithRole(RegisterWithRoleRequest request) {


        User user = User.builder()
            .nombre(request.getNombre())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .active(request.getActive() == null ? Boolean.TRUE : request.getActive())
            .imagenUrl(request.getImagenUrl())
            .fechaCreacion(java.time.LocalDateTime.now())
            .build();

        String roleName = request.getRole() == null ? "ALMACENERO" : request.getRole();
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository
                        .save(Role.builder().name(roleName).description("Role created by registerWithRole").build()));

        user.setRoles(new java.util.HashSet<>());
        user.getRoles().add(role);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    @Transactional
    public void setUserActiveStatus(Integer userId, boolean active) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse editUser(Integer userId, EditUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        if (request.getActive() != null) user.setActive(request.getActive());
        if (request.getRole() != null) {
            Role role = roleRepository.findByName(request.getRole())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.getRole()).description("").build()));
            user.setRoles(new java.util.HashSet<>());
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse editUserByEmail(String email, EditUserRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (request.getActive() != null) user.setActive(request.getActive());
        if (request.getRole() != null) {
            Role role = roleRepository.findByName(request.getRole())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.getRole()).description("").build()));
            user.setRoles(new java.util.HashSet<>());
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return toUserResponse(user);
    }

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(this::toUserResponse).toList();
    }

    @Override
    public List<UserResponse> listAlmaceneros() {
        return userRepository.findAll().stream()
            .filter(u -> u.getRoles() != null && u.getRoles().stream().anyMatch(r -> "ALMACENERO".equals(r.getName())))
            .map(this::toUserResponse)
            .toList();
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .nombre(user.getNombre())
        .email(user.getEmail())
        .active(user.getActive())
        .imagenUrl(user.getImagenUrl())
        .fechaCreacion(user.getFechaCreacion())
        .roles(user.getRoles() == null ? Set.of() : user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()))
        .build();
    }
}
