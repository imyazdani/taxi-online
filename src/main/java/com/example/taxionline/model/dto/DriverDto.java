package com.example.taxionline.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DriverDto extends UserDto {
    private String car;
    private Set<TripDto> tripList = new HashSet<>();
}
