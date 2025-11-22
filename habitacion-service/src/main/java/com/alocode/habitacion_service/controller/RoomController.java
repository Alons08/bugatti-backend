package com.alocode.habitacion_service.controller;

import com.alocode.habitacion_service.entity.Room;
import com.alocode.habitacion_service.entity.RoomState;
import com.alocode.habitacion_service.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            return ResponseEntity.ok(roomService.update(id, room));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/state/{state}")
    public List<Room> getRoomsByState(@PathVariable RoomState state) {
        return roomService.findByState(state);
    }

    @GetMapping("/type/{roomTypeId}")
    public List<Room> getRoomsByType(@PathVariable Integer roomTypeId) {
        return roomService.findByRoomType(roomTypeId);
    }
}