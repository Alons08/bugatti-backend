package com.alocode.usuario_service.service;

import com.alocode.usuario_service.dto.request.RegisterWithRoleRequest;
import com.alocode.usuario_service.dto.request.EditUserRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.dto.response.UserResponse;
import java.util.List;

public interface AdminService {
    AuthResponse registerWithRole(RegisterWithRoleRequest request);
    void setUserActiveStatus(Integer userId, boolean active);
    UserResponse editUser(Integer userId, EditUserRequest request);
    UserResponse editUserByEmail(String email, EditUserRequest request);
    UserResponse getUserByEmail(String email);
    List<UserResponse> listUsers();
    List<UserResponse> listAlmaceneros();
    UserResponse getUserById(Integer userId);
}
