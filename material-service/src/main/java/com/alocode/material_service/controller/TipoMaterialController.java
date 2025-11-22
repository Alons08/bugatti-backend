package com.alocode.material_service.controller;

import com.alocode.material_service.dto.request.TipoMaterialRequest;
import com.alocode.material_service.dto.response.TipoMaterialResponse;
import com.alocode.material_service.service.TipoMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipo-materiales")
public class TipoMaterialController {
    @Autowired
    private TipoMaterialService tipoMaterialService;

    @GetMapping
    public List<TipoMaterialResponse> getAll() {
        return tipoMaterialService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMaterialResponse> getById(@PathVariable Integer id) {
        Optional<TipoMaterialResponse> tipoMaterial = tipoMaterialService.findById(id);
        return tipoMaterial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoMaterialResponse create(@RequestBody TipoMaterialRequest tipoMaterialRequest) {
        return tipoMaterialService.save(tipoMaterialRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMaterialResponse> update(@PathVariable Integer id, @RequestBody TipoMaterialRequest tipoMaterialRequest) {
        TipoMaterialResponse updated = tipoMaterialService.update(id, tipoMaterialRequest);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
