package com.example.taxionline.service;

import com.example.taxionline.model.dto.TripDto;
import com.example.taxionline.model.dto.TripRequestAvailableDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.dto.TripStateDto;

import java.util.List;

public interface TripService {
    TripDto submitRequestByPassenger(TripRequestDto tripRequestDto);
    List<TripRequestAvailableDto> getRequestedTrips(TripRequestDto tripRequestDto);
    void changeTripState(TripStateDto tripStateDto);
}
