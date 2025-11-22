package com.alocode.almacen_service.controller;

import com.alocode.almacen_service.dto.request.AlmacenRequest;
import com.alocode.almacen_service.dto.response.AlmacenResponse;
import com.alocode.almacen_service.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@RequiredArgsConstructor
public class AlmacenController {
    private final AlmacenService almacenService;

    @PostMapping
    public ResponseEntity<AlmacenResponse> create(@RequestBody AlmacenRequest request) {
        System.out.println("[AlmacenController] POST /api/almacenes ejecutado. Request: " + request);
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
}
