package com.example.taxionline.service;

import com.example.taxionline.model.dto.*;

import java.util.List;

public interface TripService {
    TripDto submitRequestByPassenger(TripRequestDto tripRequestDto);
    List<TripRequestAvailableDto> getRequestedTrips(TripRequestDto tripRequestDto);
    void changeTripState(TripStateDto tripStateDto);
    List<TripDto> listTripsByRequest(GpsLocationDto gpsLocationDto);
}
