package com.example.taxionline.controller;

import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.dto.TripDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.request.PassengerRegisterRq;
import com.example.taxionline.model.request.TripRequestRq;
import com.example.taxionline.model.response.PassengerRegisterRs;
import com.example.taxionline.model.response.TripRequestRs;
import com.example.taxionline.service.PassengerService;
import com.example.taxionline.service.TripService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final ModelMapper modelMapper;
    private final PassengerService passengerService;
    private final TripService tripService;

    @PostMapping
    public ResponseEntity<PassengerRegisterRs> register(@RequestBody PassengerRegisterRq passengerRq){
        PassengerDto passengerDto = modelMapper.map(passengerRq, PassengerDto.class);
        PassengerDto passengerResult = passengerService.register(passengerDto);

        return new ResponseEntity<>(modelMapper.map(passengerResult, PassengerRegisterRs.class), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<PassengerRegisterRs> getPassenger(@PathVariable String username){
        PassengerDto passengerResult = passengerService.getPassenger(username);

        return new ResponseEntity<>(modelMapper.map(passengerResult, PassengerRegisterRs.class), HttpStatus.OK);
    }

    @PostMapping("/{username}/trips")
    public ResponseEntity<TripRequestRs> requestTrip(@PathVariable String username,
                                                     @RequestBody TripRequestRq tripRequestRq){
        TripRequestDto tripRequestDto = new TripRequestDto();
        tripRequestDto.setUsername(username);
        tripRequestDto.setX(tripRequestRq.getX());
        tripRequestDto.setY(tripRequestRq.getY());

        TripDto tripDto = tripService.submitRequestByPassenger(tripRequestDto);

        return new ResponseEntity<>(modelMapper.map(tripDto, TripRequestRs.class), HttpStatus.OK);
    }
}
