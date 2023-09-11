package com.example.taxionline.service;

import com.example.taxionline.model.dto.TripDto;
import com.example.taxionline.model.dto.TripRequestDto;

public interface TripService {
    TripDto request(TripRequestDto tripRequestDto);
}
