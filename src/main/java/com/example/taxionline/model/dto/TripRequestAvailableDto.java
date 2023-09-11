package com.example.taxionline.model.dto;

import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripRequestAvailableDto {
    private Long id;
    private String passengerName;
    private TripStateEnum tripState;
    private GpsLocationDto gpsLocation;
    private LocalDateTime tripTime;

}
