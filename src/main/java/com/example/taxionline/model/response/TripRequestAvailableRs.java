package com.example.taxionline.model.response;

import com.example.taxionline.model.dto.GpsLocationDto;
import lombok.Data;

@Data
public class TripRequestAvailableRs {
    private Long id;
    private String passengerName;
    private GpsLocationDto gpsLocation;
}
