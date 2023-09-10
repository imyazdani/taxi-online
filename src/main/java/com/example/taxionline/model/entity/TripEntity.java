package com.example.taxionline.model.entity;


import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "trip")
@Data
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passengerEntity;

    @ManyToOne()
    @JoinColumn(name = "driver_id")
    private DriverEntity driverEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "tripState", nullable = false)
    private TripStateEnum tripState;
}
