package com.alocode.habitacion_service.service;

import com.alocode.habitacion_service.entity.RoomType;
import com.alocode.habitacion_service.repository.RoomTypeRepository;
import com.alocode.habitacion_service.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RoomTypeServiceImpl implements IRoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final RateRepository rateRepository;

    @Override
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    @Override
    public Optional<RoomType> findById(Integer id) {
        return roomTypeRepository.findById(id);
    }

    @Override
    public RoomType save(RoomType roomType) {
        if (roomType.getRate() != null && roomType.getRate().getId() != null) {
            roomType.setRate(
                rateRepository.findById(roomType.getRate().getId())
                    .orElseThrow(() -> new RuntimeException("Rate not found"))
            );
        }
        return roomTypeRepository.save(roomType);
    }

    @Override
    public RoomType update(Integer id, RoomType roomTypeDetails) {
        return roomTypeRepository.findById(id)
                .map(roomType -> {
                    roomType.setName(roomTypeDetails.getName());
                    if (roomTypeDetails.getRate() != null && roomTypeDetails.getRate().getId() != null) {
                        roomType.setRate(
                            rateRepository.findById(roomTypeDetails.getRate().getId())
                                .orElseThrow(() -> new RuntimeException("Rate not found"))
                        );
                    }
                    return roomTypeRepository.save(roomType);
                }).orElseThrow(() -> new RuntimeException("RoomType not found"));
    }

    @Override
    public void delete(Integer id) {
        roomTypeRepository.deleteById(id);
    }
}