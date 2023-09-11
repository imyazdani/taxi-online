package com.example.taxionline.model.dto;

import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripDto {
    private Long id;
    private PassengerDto passengerEntity;
    private DriverDto driverEntity;
    private TripStateEnum tripState;
    private GpsLocationDto gpsLocation;
    private LocalDateTime tripTime;
}
