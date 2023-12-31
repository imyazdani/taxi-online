package com.example.taxionline.service.impl;

import com.example.taxionline.config.AppPropertiesConfig;
import com.example.taxionline.exception.DriverException;
import com.example.taxionline.exception.TripIsNotChangeableException;
import com.example.taxionline.exception.TripNotFoundException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.*;
import com.example.taxionline.model.entity.GpsLocationEntity;
import com.example.taxionline.model.entity.TripEntity;
import com.example.taxionline.model.enums.TripStateEnum;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.TripRepository;
import com.example.taxionline.service.DriverService;
import com.example.taxionline.service.PassengerService;
import com.example.taxionline.service.TripService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceImplTest {
    @Mock
    TripRepository tripRepository;

    @Mock
    PassengerService passengerService;

    @Mock
    DriverService driverService;

    ModelMapper modelMapper;
    AppPropertiesConfig appPropertiesConfig;

    TripService tripService;
    TripEntity tripEntity;
    TripDto tripDto;
    TripRequestDto tripRequestDto;
    PassengerDto passengerDto;
    DriverDto driverDto;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        AppPropertiesConfig.Security security =
                new AppPropertiesConfig.Security("/passengers", "/drivers");
        AppPropertiesConfig.Trip trip = new AppPropertiesConfig.Trip(10, 3);
        appPropertiesConfig = new AppPropertiesConfig(security, trip);
        tripService = new TripServiceImpl(modelMapper, tripRepository,
                passengerService, driverService, appPropertiesConfig);

        tripRequestDto = new TripRequestDto();
        tripRequestDto.setUsername("mohsen");
        tripRequestDto.setX(50L);
        tripRequestDto.setY(50L);

        tripEntity = new TripEntity();
        tripEntity.setId(1L);
        tripEntity.setTripState(TripStateEnum.REQUEST);
        tripEntity.setTripTime(LocalDateTime.now());
        GpsLocationEntity gpsLocationEntity = new GpsLocationEntity();
        gpsLocationEntity.setX(50L);
        gpsLocationEntity.setY(50L);
        tripEntity.setGpsLocation(gpsLocationEntity);

        tripDto = modelMapper.map(tripEntity, TripDto.class);

        passengerDto = new PassengerDto();
        passengerDto.setId(1L);
        passengerDto.setName("mohsen yazdani");
        passengerDto.setRole(UserRoleEnum.PASSENGER);
        passengerDto.setUsername("mohsen");
        // password is: mohsen
        passengerDto.setPassword("$2a$10$fIFKI0z4HcdCzb8YkR9Dfu5g13RdYGM1ScC/ebH9JN5SVcbtDvdYW");
        passengerDto.setTripList(Set.of());

        driverDto = new DriverDto();
        driverDto.setId(2L);
        driverDto.setName("hasan yazdani");
        driverDto.setRole(UserRoleEnum.DRIVER);
        driverDto.setUsername("hasan");
        // password is: mohsen
        driverDto.setPassword("$2a$10$LoFN0c/zAAGYamn.W1co..m63kWaNzYCCDmmwH/bKsqK1iKQkbEV6");
        driverDto.setCar("206");
        driverDto.setTripList(Set.of());
    }

    @Test
    void testSubmitRequestByPassenger() {
        Mockito.when(passengerService.getPassenger(anyString())).thenReturn(passengerDto);
        Mockito.when(tripRepository.save(any())).thenReturn(tripEntity);

        TripDto tripDtoResult = tripService.submitRequestByPassenger(tripRequestDto);

        Assertions.assertNotNull(tripDtoResult);
        Assertions.assertEquals(tripDto.getId(), tripDtoResult.getId());
        Assertions.assertEquals(TripStateEnum.REQUEST, tripDtoResult.getTripState());
        Assertions.assertEquals(tripRequestDto.getX(), tripDtoResult.getGpsLocation().getX());
        Assertions.assertEquals(tripRequestDto.getY(), tripDtoResult.getGpsLocation().getY());
    }

    @Test
    void testSubmitRequestByPassenger_UserNotFound() {
        Mockito.when(passengerService.getPassenger(anyString())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> tripService.submitRequestByPassenger(tripRequestDto));
    }

    @Test
    void testGetRequestedTrips() {
        List<TripDto> tripDtoList = new ArrayList<>();
        Mockito.when(driverService.getDriver(anyString())).thenReturn(driverDto);
        Mockito.when(tripRepository.findByTripState(any())).thenReturn(List.of(tripEntity));

        List<TripRequestAvailableDto> tripList = tripService.getRequestedTrips(tripRequestDto);

        Assertions.assertNotNull(tripList);
        Assertions.assertEquals(tripList.size(), 1L);
    }

    @Test
    void testGetRequestedTrips_UserNotFound() {
        List<TripDto> tripDtoList = new ArrayList<>();
        Mockito.when(driverService.getDriver(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> tripService.getRequestedTrips(tripRequestDto));
    }

    @Test
    void testChangeTripState() {
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(1L);
        tripStateDto.setUsername("mohsen");
        tripStateDto.setTripState(TripStateEnum.REQUEST);

        Mockito.when(tripRepository.findById(anyLong())).thenReturn(Optional.of(tripEntity));
        Mockito.when(driverService.getDriver(anyString())).thenReturn(driverDto);
        Mockito.when(tripRepository.findByDriverEntityAndTripState(any(), any())).thenReturn(null);
        Mockito.when(tripRepository.save(any())).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> tripService.changeTripState(tripStateDto));
    }

    @Test
    void testChangeTripState_TripNotFound() {
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(1L);
        tripStateDto.setUsername("mohsen");
        tripStateDto.setTripState(TripStateEnum.REQUEST);

        Mockito.when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(TripNotFoundException.class, () -> tripService.changeTripState(tripStateDto));
    }

    @Test
    void testChangeTripState_TripIsNotChangeable() {
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(1L);
        tripStateDto.setUsername("mohsen");
        tripStateDto.setTripState(TripStateEnum.REQUEST);
        tripEntity.setTripState(TripStateEnum.FINISHED);

        Mockito.when(tripRepository.findById(anyLong())).thenReturn(Optional.of(tripEntity));
        Assertions.assertThrows(TripIsNotChangeableException.class, () -> tripService.changeTripState(tripStateDto));
    }

    @Test
    void testChangeTripState_UserNotFound() {
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(1L);
        tripStateDto.setUsername("mohsen");
        tripStateDto.setTripState(TripStateEnum.REQUEST);

        Mockito.when(tripRepository.findById(anyLong())).thenReturn(Optional.of(tripEntity));
        Mockito.when(driverService.getDriver(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> tripService.changeTripState(tripStateDto));
    }

    @Test
    void testChangeTripState_DriverException() {
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(2L);
        tripStateDto.setUsername("mohsen");
        tripStateDto.setTripState(TripStateEnum.REQUEST);

        Mockito.when(tripRepository.findById(anyLong())).thenReturn(Optional.of(tripEntity));
        Mockito.when(driverService.getDriver(anyString())).thenReturn(driverDto);
        Mockito.when(tripRepository.findByDriverEntityAndTripState(any(), any())).thenReturn(tripEntity);

        Assertions.assertThrows(DriverException.class, () -> tripService.changeTripState(tripStateDto));
    }

    @Test
    void listTripsByRequest() {
        GpsLocationDto gpsLocationDto = new GpsLocationDto();
        gpsLocationDto.setId(1L);
        gpsLocationDto.setX(45L);
        gpsLocationDto.setY(45L);

        Mockito.when(tripRepository.findByTripState(any())).thenReturn(List.of(tripEntity));

        List<TripDto> tripDtoList = tripService.listTripsByRequest(gpsLocationDto);
        Assertions.assertNotNull(tripDtoList);
        Assertions.assertEquals(tripDtoList.size(), 1L);
    }

    @Test
    void listTripsByRequest_NotInDistance() {
        GpsLocationDto gpsLocationDto = new GpsLocationDto();
        gpsLocationDto.setId(1L);
        gpsLocationDto.setX(30L);
        gpsLocationDto.setY(30L);

        Mockito.when(tripRepository.findByTripState(any())).thenReturn(List.of(tripEntity));

        List<TripDto> tripDtoList = tripService.listTripsByRequest(gpsLocationDto);
        Assertions.assertNotNull(tripDtoList);
        Assertions.assertEquals(tripDtoList.size(), 0L);
    }

}
