package com.alocode.habitacion_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_number", nullable = false, length = 3)
    private String number;

    @Column(name = "room_maximum_capacity", nullable = false)
    private Integer maximumCapacity;

    @Column(name = "room_description" , columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_state" , nullable = false)
    private RoomState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id" , nullable = false)
    @ToString.Exclude
    private  RoomType roomType;


}