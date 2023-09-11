package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.dto.TripDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.entity.GpsLocationEntity;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.entity.TripEntity;
import com.example.taxionline.model.enums.TripStateEnum;
import com.example.taxionline.repository.TripRepository;
import com.example.taxionline.service.PassengerService;
import com.example.taxionline.service.TripService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;
    private final PassengerService passengerService;

    @Transactional
    @Override
    public TripDto request(TripRequestDto tripRequestDto) {
        PassengerDto passengerDto = passengerService.getPassenger(tripRequestDto.getUsername());
        if (passengerDto == null){
            throw new UserNotFoundException(tripRequestDto.getUsername());
        }

        GpsLocationEntity gpsLocation = new GpsLocationEntity();
        gpsLocation.setX(tripRequestDto.getX());
        gpsLocation.setY(tripRequestDto.getY());

        TripEntity tripEntity = new TripEntity();
        tripEntity.setPassengerEntity(modelMapper.map(passengerDto, PassengerEntity.class));
        tripEntity.setGpsLocation(gpsLocation);
        tripEntity.setTripTime(LocalDateTime.now());
        tripEntity.setTripState(TripStateEnum.REQUEST);

        TripEntity tripStored = tripRepository.save(tripEntity);
        return modelMapper.map(tripStored, TripDto.class);
    }
}
