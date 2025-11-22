package com.alocode.habitacion_service.service;

import com.alocode.habitacion_service.entity.Rate;
import com.alocode.habitacion_service.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RateServiceImpl implements IRateService {

    private final RateRepository rateRepository;

    @Override
    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    @Override
    public Optional<Rate> findById(Integer id) {
        return rateRepository.findById(id);
    }

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Rate update(Integer id, Rate rateDetails) {
        return rateRepository.findById(id)
                .map(rate -> {
                    rate.setMode(rateDetails.getMode());
                    rate.setPriceBase(rateDetails.getPriceBase());
                    rate.setPriceCard(rateDetails.getPriceCard());
                    return rateRepository.save(rate);
                }).orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    @Override
    public void delete(Integer id) {
        rateRepository.deleteById(id);
    }
}