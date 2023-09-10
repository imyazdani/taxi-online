package com.example.taxionline.service;

import com.example.taxionline.model.dto.PassengerDto;

public interface PassengerService {
    PassengerDto register(PassengerDto passengerDto);
    PassengerDto getPassenger(String username);
}
