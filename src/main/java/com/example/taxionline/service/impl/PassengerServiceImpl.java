package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.PassengerRepository;
import com.example.taxionline.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final ModelMapper modelMapper;
    private final PassengerRepository passengerRepository;

    @Override
    public PassengerDto register(PassengerDto customerDto) {
        PassengerEntity passengerEntity = modelMapper.map(customerDto, PassengerEntity.class);
        passengerRepository.findByUsername(passengerEntity.getUsername()).ifPresent(passenger -> {
            throw new UserDuplicateException(passenger.getUsername());
        });

        passengerEntity.setRole(UserRoleEnum.PASSENGER);

        PassengerEntity passengerStored = passengerRepository.save(passengerEntity);
        return modelMapper.map(passengerStored, PassengerDto.class);
    }

    @Override
    public PassengerDto getPassenger(String username) {
        PassengerEntity passengerEntity = passengerRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });

        return modelMapper.map(passengerEntity, PassengerDto.class);
    }
}
