package com.alocode.habitacion_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@Table(name = "rate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Integer id;

    @Column(name = "rate_mode", nullable = false)
    private Integer mode;

    @Column(name = "rate_price_base", nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal priceBase;

    @Column(name = "rate_price_card", nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal priceCard;

}