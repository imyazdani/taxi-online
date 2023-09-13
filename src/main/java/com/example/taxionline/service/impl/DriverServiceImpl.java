package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.entity.DriverEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.DriverRepository;
import com.example.taxionline.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public DriverDto register(DriverDto driverDto) {
        DriverEntity driverEntity = modelMapper.map(driverDto, DriverEntity.class);
        driverRepository.findByUsername(driverEntity.getUsername()).ifPresent(driver -> {
            throw new UserDuplicateException(driver.getUsername());
        });

        driverEntity.setRole(UserRoleEnum.DRIVER);
        driverEntity.setPassword(passwordEncoder.encode(driverDto.getPassword()));

        DriverEntity driverStored = driverRepository.save(driverEntity);
        log.info("Driver {} is saved successfully.", driverDto.getUsername());
        return modelMapper.map(driverStored, DriverDto.class);
    }

    @Override
    public DriverDto getDriver(String username) {
        DriverEntity driverEntity = driverRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });

        log.info("Driver {} gets information from DB.", username);
        return modelMapper.map(driverEntity, DriverDto.class);
    }
}
