package com.alocode.material_service.controller;

import com.alocode.material_service.dto.request.MaterialRequest;
import com.alocode.material_service.dto.response.MaterialResponse;
import com.alocode.material_service.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materiales")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<MaterialResponse> getAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getById(@PathVariable Integer id) {
        Optional<MaterialResponse> material = materialService.findById(id);
        return material.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MaterialResponse create(@RequestBody MaterialRequest request) {
        return materialService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> update(@PathVariable Integer id, @RequestBody MaterialRequest request) {
        MaterialResponse updated = materialService.update(id, request);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
