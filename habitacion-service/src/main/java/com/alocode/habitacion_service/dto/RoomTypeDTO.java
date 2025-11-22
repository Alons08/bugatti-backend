package com.alocode.habitacion_service.dto;

import  lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeDTO {

    private Integer id;
    private String name;
    private Integer rateId;

}