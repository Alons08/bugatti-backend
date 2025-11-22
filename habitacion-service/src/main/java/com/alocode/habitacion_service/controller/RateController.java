package com.alocode.habitacion_service.controller;

import com.alocode.habitacion_service.entity.Rate;
import com.alocode.habitacion_service.service.IRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final IRateService rateService;

    @GetMapping
    public List<Rate> getAllRates() {
        return rateService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable Integer id) {
        return rateService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rate createRate(@RequestBody Rate rate) {
        return rateService.save(rate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rate> updateRate(@PathVariable Integer id, @RequestBody Rate rate) {
        try {
            return ResponseEntity.ok(rateService.update(id, rate));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Integer id) {
        rateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}