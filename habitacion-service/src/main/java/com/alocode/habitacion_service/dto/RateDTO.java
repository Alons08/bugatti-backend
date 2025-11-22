package com.alocode.habitacion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateDTO {

    private Integer id;
    private Integer mode;
    private java.math.BigDecimal basePrice;
    private java.math.BigDecimal cardPrice;

}