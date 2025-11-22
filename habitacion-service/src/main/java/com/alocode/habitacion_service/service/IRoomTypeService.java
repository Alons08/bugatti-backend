package com.alocode.habitacion_service.service;

import com.alocode.habitacion_service.entity.RoomType;

import java.util.List;
import java.util.Optional;

public interface IRoomTypeService {
    List<RoomType> findAll();
    Optional<RoomType> findById(Integer id);
    RoomType save(RoomType roomType);
    RoomType update(Integer id, RoomType roomTypeDetails);
    void delete(Integer id);
}