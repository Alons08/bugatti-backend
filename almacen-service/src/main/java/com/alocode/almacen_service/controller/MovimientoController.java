package com.alocode.almacen_service.controller;

import com.alocode.almacen_service.dto.request.MovimientoRequest;
import com.alocode.almacen_service.dto.response.MovimientoResponse;
import com.alocode.almacen_service.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoResponse> create(@RequestBody MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.createMovimiento(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.getMovimientoById(id));
    }

    @GetMapping("/almacen/{idAlmacen}")
    public ResponseEntity<List<MovimientoResponse>> getByAlmacen(@PathVariable Integer idAlmacen) {
        return ResponseEntity.ok(movimientoService.getMovimientosByAlmacen(idAlmacen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
