package com.example.taxionline.controller;

import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.dto.TripRequestAvailableDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.request.DriverRegisterRq;
import com.example.taxionline.model.request.TripRequestRq;
import com.example.taxionline.model.response.DriverRegisterRs;
import com.example.taxionline.model.response.TripRequestAvailableRs;
import com.example.taxionline.service.DriverService;
import com.example.taxionline.service.TripService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DriverRegisterRs> register(@RequestBody DriverRegisterRq driverRq){
        DriverDto driverDto = modelMapper.map(driverRq, DriverDto.class);
        DriverDto driverResult = driverService.register(driverDto);

        return new ResponseEntity<>(modelMapper.map(driverResult, DriverRegisterRs.class), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<DriverRegisterRs> getDriver(@PathVariable String username){
        DriverDto driverResult = driverService.getDriver(username);

        return new ResponseEntity<>(modelMapper.map(driverResult, DriverRegisterRs.class), HttpStatus.OK);
    }

    @PostMapping("/{username}/trips")
    public ResponseEntity<List<TripRequestAvailableRs>> getRequestedTrips(@PathVariable String username,
                                                                          @RequestBody TripRequestRq tripRequestRq){
        TripRequestDto tripRequestDto = new TripRequestDto();
        tripRequestDto.setUsername(username);
        tripRequestDto.setX(tripRequestRq.getX());
        tripRequestDto.setY(tripRequestRq.getY());
        List<TripRequestAvailableDto> tripDtoList = tripService.getRequestedTrips(tripRequestDto);

        List<TripRequestAvailableRs> tripList = modelMapper.map(tripDtoList, new TypeToken<List<TripRequestAvailableRs>>(){}.getType());

        return new ResponseEntity<>(tripList, HttpStatus.OK);
    }
}
