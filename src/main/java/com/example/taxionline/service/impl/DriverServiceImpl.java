package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.entity.DriverEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.DriverRepository;
import com.example.taxionline.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;

    @Transactional
    @Override
    public DriverDto register(DriverDto driverDto) {
        DriverEntity driverEntity = modelMapper.map(driverDto, DriverEntity.class);
        driverRepository.findByUsername(driverEntity.getUsername()).ifPresent(driver -> {
            throw new UserDuplicateException(driver.getUsername());
        });

        driverEntity.setRole(UserRoleEnum.DRIVER);

        DriverEntity driverStored = driverRepository.save(driverEntity);
        return modelMapper.map(driverStored, DriverDto.class);
    }

    @Override
    public DriverDto getDriver(String username) {
        DriverEntity driverEntity = driverRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });

        return modelMapper.map(driverEntity, DriverDto.class);
    }
}
