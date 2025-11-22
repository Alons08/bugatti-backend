package com.alocode.habitacion_service.repository;

import java.util.List;

import com.alocode.habitacion_service.entity.Room;
import com.alocode.habitacion_service.entity.RoomState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByState(RoomState state);

    List<Room> findByRoomType_Id(Integer roomTypeId);

}