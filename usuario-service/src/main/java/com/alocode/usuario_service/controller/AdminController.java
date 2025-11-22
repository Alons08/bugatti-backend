package com.alocode.usuario_service.controller;

import com.alocode.usuario_service.dto.request.RegisterWithRoleRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.dto.response.UserResponse;
import com.alocode.usuario_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin") // Ruta protegida para ADMIN
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.ok(adminService.listUsers());
    }

    @GetMapping("/almaceneros")
    public ResponseEntity<List<UserResponse>> listAlmaceneros() {
        return ResponseEntity.ok(adminService.listAlmaceneros());
    }

    // Endpoint eliminado: listCustomers

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PostMapping(value = "register-with-role")
    public ResponseEntity<AuthResponse> registerWithRole(@RequestBody RegisterWithRoleRequest request){
        return ResponseEntity.ok(((AdminService)adminService).registerWithRole(request));
    }

    @PutMapping("/user/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Integer id) {
        adminService.setUserActiveStatus(id, true);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Integer id) {
        adminService.setUserActiveStatus(id, false);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> editUser(@PathVariable Integer id, @RequestBody com.alocode.usuario_service.dto.request.EditUserRequest request) {
        return ResponseEntity.ok(adminService.editUser(id, request));
    }

    @PutMapping("/user/email/{email}")
    public ResponseEntity<UserResponse> editUserByEmail(@PathVariable String email, @RequestBody com.alocode.usuario_service.dto.request.EditUserRequest request) {
        return ResponseEntity.ok(adminService.editUserByEmail(email, request));
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(adminService.getUserByEmail(email));
    }

}
