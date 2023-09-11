package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.*;
import com.example.taxionline.model.entity.GpsLocationEntity;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.entity.TripEntity;
import com.example.taxionline.model.enums.TripStateEnum;
import com.example.taxionline.repository.TripRepository;
import com.example.taxionline.service.DriverService;
import com.example.taxionline.service.PassengerService;
import com.example.taxionline.service.TripService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;
    private final PassengerService passengerService;
    private final DriverService driverService;

    @Transactional
    @Override
    public TripDto submitRequestByPassenger(TripRequestDto tripRequestDto) {
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

    @Override
    public List<TripRequestAvailableDto> getRequestedTrips(TripRequestDto tripRequestDto) {
        DriverDto driverDto = driverService.getDriver(tripRequestDto.getUsername());
        if (driverDto == null){
            throw new UserNotFoundException(tripRequestDto.getUsername());
        }

        GpsLocationDto gpsLocationDto = new GpsLocationDto();
        gpsLocationDto.setX(tripRequestDto.getX());
        gpsLocationDto.setY(tripRequestDto.getY());
        List<TripDto> tripDtoList = listTripsByRequest(gpsLocationDto);

        return modelMapper.map(tripDtoList, new TypeToken<List<TripRequestAvailableDto>>(){}.getType());
    }

    private List<TripDto> listTripsByRequest(GpsLocationDto gpsLocationDto){
        List<TripEntity> tripEntityList = tripRepository.findByTripState(TripStateEnum.REQUEST);

        List<TripEntity> collectedTrip = tripEntityList.parallelStream()
                .filter(t -> (Math.abs(((t.getGpsLocation().getX()-gpsLocationDto.getX()) + (t.getGpsLocation().getY()- gpsLocationDto.getY())))) <= 10)
                .limit(3)
                .collect(Collectors.toList());

        return modelMapper.map(collectedTrip, new TypeToken<List<TripDto>>(){}.getType());
    }
}
