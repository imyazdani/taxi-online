package com.example.taxionline.controller;

import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.dto.TripDto;
import com.example.taxionline.model.dto.TripRequestDto;
import com.example.taxionline.model.request.PassengerRegisterRq;
import com.example.taxionline.model.request.TripRequestRq;
import com.example.taxionline.model.response.DriverRegisterRs;
import com.example.taxionline.model.response.PassengerRegisterRs;
import com.example.taxionline.model.response.TripRequestRs;
import com.example.taxionline.service.PassengerService;
import com.example.taxionline.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class PassengerController {

    private final ModelMapper modelMapper;
    private final PassengerService passengerService;
    private final TripService tripService;

    @PostMapping
    @Operation(summary = "Register a passenger information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger register successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DriverRegisterRs.class)) }),
            @ApiResponse(responseCode = "409", description = "Passenger username is already exist",
                    content = @Content)})
    public ResponseEntity<PassengerRegisterRs> register(@RequestBody PassengerRegisterRq passengerRq){
        PassengerDto passengerDto = modelMapper.map(passengerRq, PassengerDto.class);
        PassengerDto passengerResult = passengerService.register(passengerDto);

        return new ResponseEntity<>(modelMapper.map(passengerResult, PassengerRegisterRs.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @GetMapping("/infos")
    @Operation(summary = "Get a passenger information by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the passenger information successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DriverRegisterRs.class)) }),
            @ApiResponse(responseCode = "404", description = "Passenger username not found",
                    content = @Content)})
    public ResponseEntity<PassengerRegisterRs> getPassenger(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PassengerDto passengerResult = passengerService.getPassenger(username);

        return new ResponseEntity<>(modelMapper.map(passengerResult, PassengerRegisterRs.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PostMapping("/trips")
    @Operation(summary = "Submit a trip by passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "submit the trip information successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DriverRegisterRs.class)) }),
            @ApiResponse(responseCode = "404", description = "Passenger username not found",
                    content = @Content)})
    public ResponseEntity<TripRequestRs> requestTrip(@RequestBody TripRequestRq tripRequestRq){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        TripRequestDto tripRequestDto = new TripRequestDto();
        tripRequestDto.setUsername(username);
        tripRequestDto.setX(tripRequestRq.getX());
        tripRequestDto.setY(tripRequestRq.getY());

        TripDto tripDto = tripService.submitRequestByPassenger(tripRequestDto);

        return new ResponseEntity<>(modelMapper.map(tripDto, TripRequestRs.class), HttpStatus.OK);
    }
}
