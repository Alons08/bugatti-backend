package com.alocode.habitacion_service.controller;

import com.alocode.habitacion_service.entity.RoomType;
import com.alocode.habitacion_service.service.IRoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final IRoomTypeService roomTypeService;
    @GetMapping
    public List<RoomType> getAllRoomTypes() {
        return roomTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Integer id) {
        return roomTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RoomType createRoomType(@RequestBody RoomType roomType) {
        return roomTypeService.save(roomType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable Integer id, @RequestBody RoomType roomType) {
        try {
            return ResponseEntity.ok(roomTypeService.update(id, roomType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Integer id) {
        roomTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}