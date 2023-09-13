package com.example.taxionline.controller;

import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.dto.TripRequestAvailableDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.dto.TripStateDto;
import com.example.taxionline.model.request.DriverRegisterRq;
import com.example.taxionline.model.request.TripRequestRq;
import com.example.taxionline.model.request.TripStateRq;
import com.example.taxionline.model.response.DriverRegisterRs;
import com.example.taxionline.model.response.TripRequestAvailableRs;
import com.example.taxionline.service.DriverService;
import com.example.taxionline.service.TripService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final ModelMapper modelMapper;
    private final DriverService driverService;
    private final TripService tripService;

    @PostMapping
    public ResponseEntity<DriverRegisterRs> register(@RequestBody DriverRegisterRq driverRq) {
        DriverDto driverDto = modelMapper.map(driverRq, DriverDto.class);
        DriverDto driverResult = driverService.register(driverDto);

        return new ResponseEntity<>(modelMapper.map(driverResult, DriverRegisterRs.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @GetMapping("/infos")
    public ResponseEntity<DriverRegisterRs> getDriver() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DriverDto driverResult = driverService.getDriver(username);

        return new ResponseEntity<>(modelMapper.map(driverResult, DriverRegisterRs.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PostMapping("/trips")
    public ResponseEntity<List<TripRequestAvailableRs>> getRequestedTrips(@RequestBody TripRequestRq tripRequestRq) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        TripRequestDto tripRequestDto = new TripRequestDto();
        tripRequestDto.setUsername(username);
        tripRequestDto.setX(tripRequestRq.getX());
        tripRequestDto.setY(tripRequestRq.getY());
        List<TripRequestAvailableDto> tripDtoList = tripService.getRequestedTrips(tripRequestDto);

        List<TripRequestAvailableRs> tripList = modelMapper.map(tripDtoList, new TypeToken<List<TripRequestAvailableRs>>() {
        }.getType());

        return new ResponseEntity<>(tripList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PostMapping("/trips/{id}")
    public ResponseEntity<Void> changeTripState(@PathVariable Long id,
                                                  @RequestBody TripStateRq tripStateRq) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TripStateDto tripStateDto = new TripStateDto();
        tripStateDto.setId(id);
        tripStateDto.setUsername(username);
        tripStateDto.setTripState(tripStateRq.getTripState());

        tripService.changeTripState(tripStateDto);
        return ResponseEntity.ok().build();
    }

}
