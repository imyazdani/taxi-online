package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserDuplicateException;
import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.PassengerDto;
import com.example.taxionline.model.entity.PassengerEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.PassengerRepository;
import com.example.taxionline.service.PassengerService;
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
public class PassengerServiceImplTest {
    @Mock
    PassengerRepository passengerRepository;

    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    PassengerService passengerService;
    PassengerEntity passengerEntity;
    PassengerDto passengerDto;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        passengerService = new PassengerServiceImpl(modelMapper, passengerRepository, passwordEncoder);

        passengerEntity = new PassengerEntity();
        passengerEntity.setId(1L);
        passengerEntity.setName("mohsen yazdani");
        passengerEntity.setRole(UserRoleEnum.PASSENGER);
        passengerEntity.setUsername("mohsen");
        // password is: mohsen
        passengerEntity.setPassword("$2a$10$fIFKI0z4HcdCzb8YkR9Dfu5g13RdYGM1ScC/ebH9JN5SVcbtDvdYW");
        passengerEntity.setTripList(Set.of());

        passengerDto = modelMapper.map(passengerEntity, PassengerDto.class);
    }

    @Test
    void testRegister() {
        Mockito.when(passengerRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Mockito.when(passengerRepository.save(any())).thenReturn(passengerEntity);

        PassengerDto passengerDtoResult = passengerService.register(passengerDto);

        Assertions.assertNotNull(passengerDtoResult);
        Assertions.assertEquals(passengerDto.getId(), passengerDtoResult.getId());
        Assertions.assertEquals(passengerDto.getName(), passengerDtoResult.getName());
        Assertions.assertEquals(passengerDto.getUsername(), passengerDtoResult.getUsername());
        Assertions.assertEquals(passengerDto.getRole(), passengerDtoResult.getRole());
    }

    @Test
    void testRegister_DuplicateFound() {
        Mockito.when(passengerRepository.findByUsername(anyString())).thenReturn(Optional.of(passengerEntity));
        Assertions.assertThrows(UserDuplicateException.class, () -> passengerService.register(passengerDto));
    }

    @Test
    void testGetPassenger() {
        String username = "mohsen";
        Mockito.when(passengerRepository.findByUsername(anyString())).thenReturn(Optional.of(passengerEntity));

        PassengerDto passengerDto = passengerService.getPassenger(username);

        Assertions.assertNotNull(passengerDto);
        Assertions.assertEquals(passengerEntity.getId(), passengerDto.getId());
        Assertions.assertEquals(passengerEntity.getName(), passengerDto.getName());
        Assertions.assertEquals(passengerEntity.getUsername(), passengerDto.getUsername());
        Assertions.assertEquals(passengerEntity.getPassword(), passengerDto.getPassword());
        Assertions.assertEquals(passengerEntity.getRole(), passengerDto.getRole());
    }

    @Test
    void testGetPassenger_UserNotFound() {
        String username = "mohsen";
        Mockito.when(passengerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> passengerService.getPassenger(username));
    }
}
