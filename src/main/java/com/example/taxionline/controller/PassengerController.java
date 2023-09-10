package com.example.taxionline.controller;

import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.request.PassengerRegisterRq;
import com.example.taxionline.model.response.PassengerRegisterRs;
import com.example.taxionline.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final ModelMapper modelMapper;
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerRegisterRs> register(@RequestBody PassengerRegisterRq passengerRq){
        PassengerDto passengerDto = modelMapper.map(passengerRq, PassengerDto.class);
        PassengerDto passengerResult = passengerService.register(passengerDto);

        return new ResponseEntity<>(modelMapper.map(passengerResult, PassengerRegisterRs.class), HttpStatus.CREATED);
    }
}
