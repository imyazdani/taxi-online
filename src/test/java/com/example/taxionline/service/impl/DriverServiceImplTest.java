package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.DriverDto;
import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.entity.DriverEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.DriverRepository;
import com.example.taxionline.service.DriverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    DriverRepository driverRepository;

    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    DriverService driverService;
    DriverEntity driverEntity;
    DriverDto driverDto;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        driverService = new DriverServiceImpl(modelMapper, driverRepository, passwordEncoder);

        driverEntity = new DriverEntity();
        driverEntity.setId(1L);
        driverEntity.setName("mohsen yazdani");
        driverEntity.setRole(UserRoleEnum.DRIVER);
        driverEntity.setUsername("mohsen");
        // password is: mohsen
        driverEntity.setPassword("$2a$10$fIFKI0z4HcdCzb8YkR9Dfu5g13RdYGM1ScC/ebH9JN5SVcbtDvdYW");
        driverEntity.setCar("206");
        driverEntity.setTripList(Set.of());

        driverDto = modelMapper.map(driverEntity, DriverDto.class);
    }

    @Test
    void testRegister() {
        Mockito.when(driverRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Mockito.when(driverRepository.save(any())).thenReturn(driverEntity);

        UserDto userDtoResult = driverService.register(driverDto);

        Assertions.assertNotNull(userDtoResult);
        Assertions.assertEquals(driverDto.getId(), userDtoResult.getId());
        Assertions.assertEquals(driverDto.getName(), userDtoResult.getName());
        Assertions.assertEquals(driverDto.getUsername(), userDtoResult.getUsername());
        Assertions.assertEquals(driverDto.getRole(), userDtoResult.getRole());
    }

    @Test
    void testRegister_DuplicateFound() {
        Mockito.when(driverRepository.findByUsername(anyString())).thenReturn(Optional.of(driverEntity));
        Assertions.assertThrows(UserDuplicateException.class, () -> driverService.register(driverDto));
    }

    @Test
    void testGetDriver() {
        String username = "mohsen";
        Mockito.when(driverRepository.findByUsername(anyString())).thenReturn(Optional.of(driverEntity));

        DriverDto driverDto = driverService.getDriver(username);

        Assertions.assertNotNull(driverDto);
        Assertions.assertEquals(driverEntity.getId(), driverDto.getId());
        Assertions.assertEquals(driverEntity.getName(), driverDto.getName());
        Assertions.assertEquals(driverEntity.getUsername(), driverDto.getUsername());
        Assertions.assertEquals(driverEntity.getPassword(), driverDto.getPassword());
        Assertions.assertEquals(driverEntity.getRole(), driverDto.getRole());
        Assertions.assertEquals(driverEntity.getCar(), driverDto.getCar());
    }

    @Test
    void testGetDriver_UserNotFound() {
        String username = "mohsen";
        Mockito.when(driverRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> driverService.getDriver(username));
    }
}