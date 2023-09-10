package com.example.taxionline.controller;

import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.request.DriverRegisterRq;
import com.example.taxionline.model.response.DriverRegisterRs;
import com.example.taxionline.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final ModelMapper modelMapper;
    private final DriverService driverService;

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
}
