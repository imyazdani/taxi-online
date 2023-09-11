package com.example.taxionline.model.dto;

import lombok.Data;

@Data
public class GpsLocationDto {
    private Long id;
    private Long x;
    private Long y;
    private TripDto tripDto;
}
