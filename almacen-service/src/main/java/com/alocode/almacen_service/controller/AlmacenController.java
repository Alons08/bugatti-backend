package com.alocode.almacen_service.controller;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.dto.response.EstadisticasResponse;
import com.alocode.almacen_service.dto.response.MovimientoRecienteResponse;
import com.alocode.almacen_service.service.AlmacenService;
import com.alocode.almacen_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/almacenes")
@RequiredArgsConstructor
public class AlmacenController {
    private final AlmacenService almacenService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<AlmacenResponse> create(@RequestBody AlmacenRequest request) {
        System.out.println("[AlmacenController] POST /almacenes ejecutado. Request: " + request);
        return ResponseEntity.ok(almacenService.createAlmacen(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(almacenService.getAlmacenById(id));
    }

    @GetMapping
    public ResponseEntity<List<AlmacenResponse>> getAll() {
        return ResponseEntity.ok(almacenService.getAllAlmacenes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenResponse> update(@PathVariable Integer id, @RequestBody AlmacenRequest request) {
        return ResponseEntity.ok(almacenService.updateAlmacen(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        almacenService.deleteAlmacen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasResponse> getEstadisticas() {
        return ResponseEntity.ok(almacenService.getEstadisticas());
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<AlmacenResponse>> getAlmacenesByUsuario(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        java.util.List<String> roles = jwtUtil.getRolesFromToken(jwtToken);
        if (roles == null || !roles.contains("ALMACENERO")) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        String email = jwtUtil.getUsernameFromToken(jwtToken);
        return ResponseEntity.ok(almacenService.getAlmacenesByUsuario(email));
    }

    @GetMapping("/movimientos-recientes")
    public ResponseEntity<List<MovimientoRecienteResponse>> getMovimientosRecientes(
            @RequestParam(defaultValue = "5") Integer limite) {
        return ResponseEntity.ok(almacenService.getMovimientosRecientes(limite));
    }
}
