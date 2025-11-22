package com.alocode.almacen_service.controller;

import com.alocode.almacen_service.dto.request.AlmacenMaterialRequest;
import com.alocode.almacen_service.dto.response.AlmacenMaterialResponse;
import com.alocode.almacen_service.service.AlmacenMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/almacen-material")
@RequiredArgsConstructor
public class AlmacenMaterialController {
    private final AlmacenMaterialService almacenMaterialService;

    @PostMapping
    public ResponseEntity<AlmacenMaterialResponse> createOrUpdate(@RequestBody AlmacenMaterialRequest request) {
        return ResponseEntity.ok(almacenMaterialService.createOrUpdate(request));
    }

    @GetMapping("/{idAlmacen}/{idMaterial}")
    public ResponseEntity<AlmacenMaterialResponse> getById(@PathVariable Integer idAlmacen, @PathVariable Integer idMaterial) {
        return ResponseEntity.ok(almacenMaterialService.getById(idAlmacen, idMaterial));
    }

    @GetMapping("/almacen/{idAlmacen}")
    public ResponseEntity<List<AlmacenMaterialResponse>> getByAlmacen(@PathVariable Integer idAlmacen) {
        return ResponseEntity.ok(almacenMaterialService.getByAlmacen(idAlmacen));
    }

    @DeleteMapping("/{idAlmacen}/{idMaterial}")
    public ResponseEntity<Void> delete(@PathVariable Integer idAlmacen, @PathVariable Integer idMaterial) {
        almacenMaterialService.delete(idAlmacen, idMaterial);
        return ResponseEntity.noContent().build();
    }
}
