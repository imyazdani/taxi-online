package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.PassengerRepository;
import com.example.taxionline.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final ModelMapper modelMapper;
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public PassengerDto register(PassengerDto passengerDto) {
        PassengerEntity passengerEntity = modelMapper.map(passengerDto, PassengerEntity.class);
        passengerRepository.findByUsername(passengerEntity.getUsername()).ifPresent(passenger -> {
            throw new UserDuplicateException(passenger.getUsername());
        });

        passengerEntity.setRole(UserRoleEnum.PASSENGER);
        passengerEntity.setPassword(passwordEncoder.encode(passengerDto.getPassword()));

        PassengerEntity passengerStored = passengerRepository.save(passengerEntity);
        log.info("Passenger {} is saved successfully.", passengerDto.getUsername());
        return modelMapper.map(passengerStored, PassengerDto.class);
    }

    @Override
    public PassengerDto getPassenger(String username) {
        PassengerEntity passengerEntity = passengerRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });

        log.info("Passenger {} gets information from DB.", username);
        return modelMapper.map(passengerEntity, PassengerDto.class);
    }
}
