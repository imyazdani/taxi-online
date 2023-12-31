package com.example.taxionline.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PassengerDto extends UserDto{
    private Set<TripDto> tripList = new HashSet<>();
}
