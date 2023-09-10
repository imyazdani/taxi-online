package com.example.taxionline.service;

import com.example.taxionline.model.dto.DriverDto;

public interface DriverService {
    DriverDto register(DriverDto driverDto);
    DriverDto getDriver(String username);
}
