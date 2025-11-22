package com.alocode.habitacion_service.service;

import com.alocode.habitacion_service.entity.Rate;

import java.util.List;
import java.util.Optional;

public interface IRateService {

    List<Rate> findAll();

    Optional<Rate> findById(Integer id);

    Rate save(Rate rate);

    Rate update(Integer id, Rate rateDetails);

    void delete(Integer id);

}