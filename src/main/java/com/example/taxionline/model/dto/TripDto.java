package com.example.taxionline.model.dto;

import com.example.taxionline.model.entity.DriverEntity;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

@Data
public class TripDto {
    private Long id;
    private PassengerEntity passengerEntity;
    private DriverEntity driverEntity;
    private TripStateEnum tripState;
}
