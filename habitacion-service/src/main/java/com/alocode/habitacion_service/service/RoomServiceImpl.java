package com.alocode.habitacion_service.service;

import com.alocode.habitacion_service.entity.Room;
import com.alocode.habitacion_service.entity.RoomState;
import com.alocode.habitacion_service.repository.RoomRepository;
import com.alocode.habitacion_service.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room save(Room room) {
        if (room.getRoomType() != null && room.getRoomType().getId() != null) {
            room.setRoomType(
                roomTypeRepository.findById(room.getRoomType().getId())
                    .orElseThrow(() -> new RuntimeException("RoomType not found"))
            );
        }
        return roomRepository.save(room);
    }

    @Override
    public Room update(Long id, Room roomDetails) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setNumber(roomDetails.getNumber());
                    room.setMaximumCapacity(roomDetails.getMaximumCapacity());
                    room.setDescription(roomDetails.getDescription());
                    room.setState(roomDetails.getState());
                    if (roomDetails.getRoomType() != null && roomDetails.getRoomType().getId() != null) {
                        room.setRoomType(
                            roomTypeRepository.findById(roomDetails.getRoomType().getId())
                                .orElseThrow(() -> new RuntimeException("RoomType not found"))
                        );
                    }
                    return roomRepository.save(room);
                }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findByState(RoomState state) {
        return roomRepository.findByState(state);
    }

    @Override
    public List<Room> findByRoomType(Integer roomTypeId) {
        return roomRepository.findByRoomType_Id(roomTypeId);
    }
}